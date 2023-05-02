package pro.sky.telegrambot.listener;


import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.constants.ReportStatus;
import pro.sky.telegrambot.model.Adopter;
import pro.sky.telegrambot.model.BranchParams;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.repository.*;
import pro.sky.telegrambot.service.BranchService;
import pro.sky.telegrambot.service.VolunteerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambot.constants.Constants.*;

@ExtendWith(MockitoExtension.class)

 class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Mock
    private BranchService branchService;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private AdopterRepository adopterRepository;

    @Mock
    private PetReportRepository petReportRepository;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ServiceTableRepository serviceTableRepository;



    /* Тест команды '/start' от старого пользователя */
    @Test
    public void handleStartCommandOldGuestTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(WELCOME_MESSAGE);
    }


    /* Тест команды '/start' от нового пользователя */
    @Test
    public void handleStartCommandNewGuestTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update_diff_id.json").toURI()));
        Update update = getUpdateMessage(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(WELCOME_MESSAGE);
    }

    /* Тест нажатия кнопки выбора кошачьего приюта */
    @Test
    public void handleCatShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_SELECT_CAT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_SELECT_CAT+ ICON_CAT);
    }

    /* Тест нажатия кнопки выбора собачьего приюта */
    @Test
    public void handleDogShelterSelectTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_SELECT_DOG);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(2)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_SELECT_DOG_AND_BECOME_ADOPTER + "?");
    }

    /* Тест призыва волонтера */
    @Test
    public void handleVolunteerCallTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, ACTION_VOLUNTEER_ICON_CLICKED);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(ACTION_VOLUNTEER_ICON_CLICKED);
    }

    /* Тест вызов инструкций усыновителя */
    @Test
    public void handleBecomeAdopterInstructionTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_BECOME_ADOPTER_INSTRUCTIONS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(ADOPTER_SAVE_INSTRUCTION);
    }

    /* Тест инструкций знакомства с петом */
    @Test
    public void handleMeetPetRulesInstructionTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_GET_TO_KNOW_RULES);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_GET_TO_KNOW_RULES+ ": " + serviceTableRepository.getMeetPetRules());
    }

    /* Тест набора документов для усыновления пета */
    @Test
    public void handleDocsListForAdoptionTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_DOCS_SET);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_DOCS_SET + ": " + serviceTableRepository.getAdoptionDocs());
    }

    /* Тест правила перевозки пета */
    @Test
    public void handleTransportationPetRulesTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_TRANSPORTATION_RULES);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_TRANSPORTATION_RULES + ": " + serviceTableRepository.getTransportPet());
    }

    /* Тест ухода за щенком */
    @Test
    public void handleSmallPetCareRulesTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForPuppy());
    }

    /* Тест ухода за взрослым питомцем */
    @Test
    public void handleBigPetCareRulesTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForBigDog());
    }

    /* Тест ухода за питомцем-инвалидом */
    @Test
    public void handleHandicappedPetCareRulesTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS + ": " + serviceTableRepository.getHouseForHandicappedDog());
    }

    /* Тест инструкции кинологов */
    @Test
    public void handlePetAdviserRecommendationsTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_SPECIALIST_ADVICE);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_SPECIALIST_ADVICE + ": " + serviceTableRepository.getSpecialistAdvice());
    }

    /* Тест контакты кинологов */
    @Test
    public void handlePetAdviserContactsTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_SPECIALIST_CONTACTS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_SPECIALIST_CONTACTS + ": " + serviceTableRepository.getSpecialistContact());
    }

    /* Тест причин отказа в усыновлении */
    @Test
    public void handlePetAdoptionRefusalReasonsTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_REFUSAL_REASONS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(CLICKED_ICON_PETS_REFUSAL_REASONS + ": " + serviceTableRepository.getRefusalReasons());
    }

    /* Тест инструкций заполнения ежедневных отчетов о питомце */
    @Test
    public void handleDailyAdoptionReportInstructionsTest() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, ICON_PETS_REPORT_INSTRUCTIONS_CLICKED);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(PET_REPORT_INSTRUCTION);
    }

    /* Тест информации о приюте */
    @Test
    public void handleShelterInformationTest() throws URISyntaxException, IOException {
        BranchParams branchParams = new BranchParams(1L,"Test Branch");
        when(branchService.findBranchById(1)).thenReturn(branchParams);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_SHELTER_INFO_TEXT);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo(branchService.findBranchById(1).getName() + ICON_WAVE + ICON_SMILE );
    }

    /* Поделиться контактами гостя - тест кнопки */
    @Test
    public void handleShareGuestContact() throws URISyntaxException, IOException {
        Long chatId = 9876543210L;

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("contact_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_LEAVE_CONTACT_DETAILS);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(chatId);
        }


    @Test
    public void handleButtonSendPetReportClick() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("data_update.json").toURI()));
        Update update = getUpdateMessage(json, CLICKED_ICON_PETS_REPORT_SEND);
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot, Mockito.times(1)).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(LOAD_PHOTO_MESSAGE);
    }

    @Test
    public void savePetReportDietTest() throws URISyntaxException, IOException {
        Long chatId = 9876543210L;
        LocalDate today = LocalDate.now();

        Adopter adopter = new Adopter(1L,
                "Alex",
                "Klepov",
                "4519189654",
                49,
                "123456987",
                "a_klepov@rambler.ru",
                9876543210L,
                null,
                false,
                true);

        PetReport petReport = new PetReport(1L,adopter,
                today,
                null,
                null,
                null,
                null);

        when(petReportRepository.findPetReportByAdopterIdAndReportDate(any(Adopter.class), any(LocalDate.class))).thenReturn(petReport);
        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);
        ReportStatus reportStatus = ReportStatus.WAITING_FOR_PET_DIET;

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json,  "qwerty");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(SAVED_DIET_MESSAGE);
    }

    @Test
    public void savePetReportLifeStatusTest() throws URISyntaxException, IOException {
        Long chatId = 9876543210L;
        LocalDate today = LocalDate.now();

        Adopter adopter = new Adopter(1L,
                "Alex",
                "Klepov",
                "4519189654",
                49,
                "123456987",
                "a_klepov@rambler.ru",
                9876543210L,
                null,
                false,
                true);

        PetReport petReport = new PetReport(1L,adopter,
                today,
                null,
                null,
                null,
                null);

        when(petReportRepository.findPetReportByAdopterIdAndReportDate(any(Adopter.class), any(LocalDate.class))).thenReturn(petReport);
        when(adopterRepository.findByChatId(any(Long.class))).thenReturn(adopter);
        ReportStatus reportStatus = ReportStatus.WAITING_FOR_LIFE_STATUS;

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdateMessage(json,  "qwerty");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getParameters().get("chat_id")).isEqualTo(1234567890L);
        Assertions.assertThat(actual.getParameters().get("text"))
                .isEqualTo(SAVED_LIFE_STATUS_MESSAGE);
    }


    private Update getUpdateMessage(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%message_text%", replaced), Update.class);
    }


}
