package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.telegrambot.constants.ReportStatus;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.Guest;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.repository.*;
import pro.sky.telegrambot.service.BranchService;
import pro.sky.telegrambot.service.PetAvatarService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static pro.sky.telegrambot.constants.Constants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private ReportStatus reportStatus = ReportStatus.DEFAULT;

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }
    public ReportStatus getReportStatus() {
        return reportStatus;
    }


    @Autowired
    private TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;
    private final AdopterRepository adopterRepository;
    private final GuestRepository guestRepository;
    private final ServiceTableRepository serviceTableRepository;

    private final PetReportRepository petReportRepository;
    private final PetRepository petRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, VolunteerRepository volunteerRepository, AdopterRepository adopterRepository, GuestRepository guestRepository, ServiceTableRepository serviceTableRepository, PetReportRepository petReportRepository, PetRepository petRepository, BranchService branchService, PetAvatarService petAvatarService) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
        this.adopterRepository = adopterRepository;
        this.guestRepository = guestRepository;
        this.serviceTableRepository = serviceTableRepository;
        this.petReportRepository = petReportRepository;
        this.petRepository = petRepository;
        this.branchService = branchService;
        this.petAvatarService = petAvatarService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private final BranchService branchService;
    private final PetAvatarService petAvatarService;

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                String incomeMsgText = update.message().text();
                if (incomeMsgText == null) {
                    return;
                }

                if (reportStatus == ReportStatus.WAITING_FOR_PET_PHOTO) {
                    savePetReportPhoto(update);
                    reportStatus = ReportStatus.WAITING_FOR_PET_DIET;
                    return;
                }
                if (reportStatus == ReportStatus.WAITING_FOR_PET_DIET) {
                    savePetReportDiet(update);
                    reportStatus = ReportStatus.WAITING_FOR_LIFE_STATUS;
                    return;
                }
                if (reportStatus == ReportStatus.WAITING_FOR_LIFE_STATUS) {
                    savePetReportLifeStatus(update);
                    reportStatus = ReportStatus.WAITING_FOR_BEHAVIOR;
                    return;
                }
                if (reportStatus == ReportStatus.WAITING_FOR_BEHAVIOR) {
                    savePetReportBehavior(update);
                    reportStatus = ReportStatus.DEFAULT;
                    return;
                }

                if (update.message().text() == null) {
                    // For stickers incomeMsgText is null
                    return;
                }

                long chatId = update.message().chat().id();
                if (incomeMsgText.equals("/start")) {
                    SendMessage message = new SendMessage(chatId, WELCOME_MESSAGE);
                    // запомнить гостя
                    long g_id = update.message().chat().id();
                    int lastMenu = update.updateId();
                    Guest guest = guestRepository.findByChatId(g_id);
                    if (guest == null) {
                        guest = new Guest(g_id, new Timestamp(System.currentTimeMillis()), lastMenu);
                        guestRepository.save(guest);
                    }
                    // Добавляем кнопки
                    message.replyMarkup(createIconsStage0());
                    sendMessage(message);
                }
            }
            // Нажатия кнопок
            else {
                processIconClick(update);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    // Сервисные sendMessage
    private void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (response != null && !response.isOk()) {
            logger.warn("Message was not sent: {}, error code: {}", message, response.errorCode());
        }
    }

    private void sendPhoto(SendPhoto sendPhoto) {
        SendResponse response = telegramBot.execute(sendPhoto);
        if (response != null && !response.isOk()) {
            logger.warn("Photo was not sent: {}, error code: {}", sendPhoto, response.errorCode());
        }
    }

    private void sendPhotoOnIconClick(long chatId, byte[] sendPhoto) {
        sendPhoto(new SendPhoto(chatId, sendPhoto));
    }

    private void sendMessageOnIconClick(long chatId, String message) {
        sendMessage(new SendMessage(chatId, message));
    }

    private void savePetReport(long chatId) {
        Adopter adopterId = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();

        PetReport petReport = petReportRepository.findPetReportByAdopterIdAndReportDate(adopterId, date);
        if (petReport == null) {
            petReport = new PetReport(adopterId, date, null, null, null, null);
            petReportRepository.save(petReport);
            SendMessage requestPhotoMessage = new SendMessage(chatId, LOAD_PHOTO_MESSAGE);
            sendMessage(requestPhotoMessage);
        }
        if (petReport.getBehavior() != null) {
            SendMessage message = new SendMessage(chatId, PET_REPORT_EXIST);
            sendMessage(message);
        }

    }

    private void savePetReportPhoto(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        PetReport petReport = petReportRepository.findPetReportByAdopterIdAndReportDate(adopter, date);
        if (update.message().photo() != null) {
            byte[] image = getPhoto(update);
            petReport.setPhoto(image);
            petReportRepository.save(petReport);
            SendMessage savedPhotoMessage = new SendMessage(chatId, SAVED_PHOTO_MESSAGE);
            sendMessage(savedPhotoMessage);
        }
    }

    public byte[] getPhoto(Update update) {
        if (update.message().photo() != null) {
            PhotoSize[] photoSizes = update.message().photo();
            for (PhotoSize photoSize : photoSizes) {
                GetFile getFile = new GetFile(photoSize.fileId());
                GetFileResponse getFileResponse = telegramBot.execute(getFile);
                if (getFileResponse.isOk()) {
                    File file = getFileResponse.file();
                    String extension = StringUtils.getFilenameExtension(file.filePath());
                    try {
                        byte[] image = telegramBot.getFileContent(file);
                        return image;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    private void savePetReportDiet(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        PetReport petReport = petReportRepository.findPetReportByAdopterIdAndReportDate(adopter, date);
        String diet = petReport.getDiet();
        if (diet == null) {
            String newDiet = update.message().text();
            petReport.setDiet(newDiet);
            petReportRepository.save(petReport);
            SendMessage saveDietMessage = new SendMessage(chatId, SAVED_DIET_MESSAGE);
            sendMessage(saveDietMessage);
        }
    }
    private void savePetReportLifeStatus(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        PetReport petReport = petReportRepository.findPetReportByAdopterIdAndReportDate(adopter, date);
        String lifeStatus = petReport.getLifeStatus();
        if (lifeStatus == null) {
            String newLifeStatus = update.message().text();
            petReport.setLifeStatus(newLifeStatus);
            petReportRepository.save(petReport);
            SendMessage saveLifeStatusMessage = new SendMessage(chatId, SAVED_LIFE_STATUS_MESSAGE);
            sendMessage(saveLifeStatusMessage);
        }
    }

    private void savePetReportBehavior(Update update) {
        long chatId = update.message().chat().id();
        Adopter adopter = adopterRepository.findByChatId(chatId);
        LocalDate date = LocalDate.now();
        PetReport petReport = petReportRepository.findPetReportByAdopterIdAndReportDate(adopter, date);
        String behavior = petReport.getBehavior();
        if (behavior == null) {
            String newBehavior = update.message().text();
            petReport.setBehavior(newBehavior);
            petReportRepository.save(petReport);
            SendMessage saveBehaviorMessage = new SendMessage(chatId, SAVED_BEHAVIOR_MESSAGE);
            sendMessage(saveBehaviorMessage);
        }
    }


    /**
     * Process icon clicks from guests.
     *
     * @param update shelter guest input (icon click, etc.)
     *               if icon is clicked - {@code callbackQuery.data()}
     * @return message to the guest
     * @see InlineKeyboardButton#callbackData()
     */

    // Нажатия кнопок всех этапов
    private void processIconClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                // запрос информации о приюте - Этап 1
                case ACTION_STAGE1_ICON_CLICKED -> createButtonsStage1(chatId);
                // отработка нажатий кнопок по запросу информации о приюте - Этап 1
                case CLICKED_ICON_SHELTER_INFO_TEXT -> sendMessageOnIconClick(chatId,branchService.findBranchById(1).getName() + ICON_WAVE + ICON_SMILE ); // краткая информация о приюте
                case CLICKED_ICON_SHELTER_CONTACT_DETAILS -> sendMessageOnIconClick(chatId,branchService.findBranchById(1).getCountry() + ", " + branchService.findBranchById(1).getCity() + ", " + branchService.findBranchById(1).getZip() + ", " + branchService.findBranchById(1).getAddress() + ", работаем: " + branchService.findBranchById(1).getWorkHours()); // контактная информация
                case CLICKED_ICON_SAFETY_SHELTER_RULES -> sendMessageOnIconClick(chatId,CLICKED_ICON_SAFETY_SHELTER_RULES + " " + branchService.findBranchById(1).getInfo());// правила приюта
                case CLICKED_ICON_LEAVE_CONTACT_DETAILS -> {sendMessageOnIconClick(chatId,CLICKED_ICON_LEAVE_CONTACT_DETAILS);
                    leaveContactDetails(update);}// оставить контактные данные
                // запрос информации о питомце - Этап 2
                case ACTION_STAGE2_ICON_CLICKED -> createButtonsStage2(chatId);
                // отработка нажатий кнопок по запросу информации о приюте - Этап 2
                case ICON_PETS_SELECT_DOG -> catDogSelect(chatId);
                case CLICKED_ICON_PETS_SELECT_DOG -> {
                    sendMessageOnIconClick(chatId, CLICKED_ICON_PETS_SELECT_DOG + ICON_DOG);
                    printAllVacantPets(chatId);
                    sendPhotoOnIconClick(chatId, petAvatarService.findPetAvatar(4).getData());
                }
                case CLICKED_ICON_PETS_SELECT_CAT -> sendMessageOnIconClick(chatId, CLICKED_ICON_PETS_SELECT_CAT+ ICON_CAT);
                case CLICKED_ICON_PETS_GET_TO_KNOW_RULES -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_GET_TO_KNOW_RULES + ": " + serviceTableRepository.getMeetPetRules());
                case CLICKED_ICON_PETS_DOCS_SET -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_DOCS_SET + ": " + serviceTableRepository.getAdoptionDocs());
                case CLICKED_ICON_PETS_TRANSPORTATION_RULES -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_TRANSPORTATION_RULES + ": " + serviceTableRepository.getTransportPet());
                case CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS -> sendMessageOnIconClick(chatId,CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForPuppy());
                case CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS -> sendMessageOnIconClick(chatId,CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForBigDog());
                case CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS -> sendMessageOnIconClick(chatId,CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForHandicappedDog());
                case CLICKED_ICON_PETS_SPECIALIST_ADVICE -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_SPECIALIST_ADVICE + ": " + serviceTableRepository.getSpecialistAdvice());
                case CLICKED_ICON_PETS_SPECIALIST_CONTACTS -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_SPECIALIST_CONTACTS + ": " + serviceTableRepository.getSpecialistContact());
                case CLICKED_ICON_PETS_REFUSAL_REASONS -> sendMessageOnIconClick(chatId,CLICKED_ICON_PETS_REFUSAL_REASONS + ": " + serviceTableRepository.getRefusalReasons());
                // отправка отчета о питомце - Этап 3
                case ACTION_STAGE3_ICON_CLICKED -> createButtonsStage3(chatId);
                // отработка нажатий кнопок по отправке отчета о питомце - Этап 3
                case ICON_PETS_REPORT_INSTRUCTIONS_CLICKED -> {SendMessage instructionsForPetReport = new SendMessage(chatId, PET_REPORT_INSTRUCTION);
                sendMessage(instructionsForPetReport);}
                case CLICKED_ICON_PETS_REPORT_SEND -> {reportStatus = ReportStatus.WAITING_FOR_PET_PHOTO;
                    savePetReport(chatId);
                }
                case ACTION_VOLUNTEER_ICON_CLICKED -> sendMessageOnIconClick(chatId, ACTION_VOLUNTEER_ICON_CLICKED); // позвать волонтера
            }
        }
    }

    // Создаем меню Этапа 1
    private void createButtonsStage1(long chatId) {
        SendMessage message = new SendMessage(chatId, ACTION_STAGE1_ICON_CLICKED);
        message.replyMarkup(createIconsStage1());
        sendMessage(message);
    }

    private void leaveContactDetails(Update update) {
        long chatId = update.callbackQuery().from().id();
        SendMessage message = new SendMessage(chatId, CLICKED_ICON_LEAVE_CONTACT_DETAILS);
        message.replyMarkup(createLeaveContactDetailsKeyboardButton());
        sendMessage(message);
    }

    // Создаем меню Этапа 2
    private void createButtonsStage2(long chatId) {
        SendMessage message = new SendMessage(chatId, ACTION_STAGE2_ICON_CLICKED);
        message.replyMarkup(createIconsStage2());
        sendMessage(message);
    }

    private void createButtonsStage3(long chatId) {
        SendMessage message = new SendMessage(chatId, ACTION_STAGE2_ICON_CLICKED);
        message.replyMarkup(createIconsStage3());
        sendMessage(message);
    }

    // Выбор собаки / кошки
    private void catDogSelect(long chatId) {
        SendMessage message = new SendMessage(chatId, ICON_PETS_SELECT_DOG + ICON_DOG + ICON_CAT + ICON_BIRD);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDog = new InlineKeyboardButton("Собаки"+ ICON_DOG);
        InlineKeyboardButton buttonCat = new InlineKeyboardButton("Кошки"+ ICON_CAT);

        inlineKeyboardMarkup.addRow(buttonDog.callbackData(CLICKED_ICON_PETS_SELECT_DOG));
        inlineKeyboardMarkup.addRow(buttonCat.callbackData(CLICKED_ICON_PETS_SELECT_CAT));
        message.replyMarkup(inlineKeyboardMarkup);
        sendMessage(message);
    }

    private void printAllVacantPets(long chatId) {
        List<Pet> vacantPets = petRepository.findVacantPet();
        for (Pet vacantPet : vacantPets) {
            long petAvatar = vacantPet.getAvatarId();
            SendMessage message = new SendMessage(chatId, vacantPet.toString());
            sendMessage(message);
            sendPhotoOnIconClick(chatId, petAvatarService.findPetAvatar(petAvatar).getData());
        }
    }

    // Создаем кнопки меню

    // Создаем кнопки меню Этапа 0

    /**
     * Answer to welcome message (to "/start" command) and guest creation - Stage 0
     *
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createIconsStage0() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_STAGE1_TEXT).callbackData(ACTION_STAGE1_ICON_CLICKED));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_STAGE2_TEXT).callbackData(ACTION_STAGE2_ICON_CLICKED));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_STAGE3_TEXT).callbackData(ACTION_STAGE3_ICON_CLICKED));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_VOLUNTEER_TEXT).callbackData(ACTION_VOLUNTEER_ICON_CLICKED));
        return inlineKeyboardMarkup;
    }

    // Создаем кнопки меню Этапа 1
    private InlineKeyboardMarkup createIconsStage1() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_SHELTER_INFO_TEXT).callbackData(CLICKED_ICON_SHELTER_INFO_TEXT));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_SHELTER_CONTACT_DETAILS).callbackData(CLICKED_ICON_SHELTER_CONTACT_DETAILS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_SAFETY_SHELTER_RULES).callbackData(CLICKED_ICON_SAFETY_SHELTER_RULES));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_LEAVE_CONTACT_DETAILS).callbackData(CLICKED_ICON_LEAVE_CONTACT_DETAILS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_VOLUNTEER_TEXT).callbackData(ACTION_VOLUNTEER_ICON_CLICKED));
        return inlineKeyboardMarkup;
    }
    // Создаем кнопки меню Этапа 2
    private InlineKeyboardMarkup createIconsStage2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_SELECT_DOG).callbackData(ICON_PETS_SELECT_DOG));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_GET_TO_KNOW_RULES).callbackData(CLICKED_ICON_PETS_GET_TO_KNOW_RULES));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_DOCS_SET).callbackData(CLICKED_ICON_PETS_DOCS_SET));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_TRANSPORTATION_RULES).callbackData(CLICKED_ICON_PETS_TRANSPORTATION_RULES));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_SMALL_PETS_HOME_RECOMMENDATIONS).callbackData(CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_BIG_PETS_HOME_RECOMMENDATIONS).callbackData(CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS).callbackData(CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_SPECIALIST_ADVICE).callbackData(CLICKED_ICON_PETS_SPECIALIST_ADVICE));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_SPECIALIST_CONTACTS).callbackData(CLICKED_ICON_PETS_SPECIALIST_CONTACTS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_REFUSAL_REASONS).callbackData(CLICKED_ICON_PETS_REFUSAL_REASONS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_SELECT_PETS).callbackData(CLICKED_ICON_SELECT_PETS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_ADOPTER_DETAILS).callbackData(CLICKED_ICON_ADOPTER_DETAILS));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_VOLUNTEER_TEXT).callbackData(ACTION_VOLUNTEER_ICON_CLICKED));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createIconsStage3() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_REPORT_INSTRUCTIONS).callbackData(ICON_PETS_REPORT_INSTRUCTIONS_CLICKED));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton(ICON_PETS_REPORT_SEND).callbackData(CLICKED_ICON_PETS_REPORT_SEND));
        return inlineKeyboardMarkup;
    }

    private ReplyKeyboardMarkup createLeaveContactDetailsKeyboardButton() {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_SHARE_CONTACT_DETAILS);
        keyboardButton.requestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }



}