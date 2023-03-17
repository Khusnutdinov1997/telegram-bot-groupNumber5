package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.VolunteerRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import static pro.sky.telegrambot.constants.Constants.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,VolunteerRepository volunteerRepository) {
        this.telegramBot = telegramBot;
        this.volunteerRepository = volunteerRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Welcome guest

            if (update.message() != null) {
                String incomeMsgText = update.message().text();
                if (incomeMsgText == null) {
                    return;
                }

                long chatId = update.message().chat().id();
                if (incomeMsgText.equals("/start")) {
                    SendMessage message = new SendMessage(chatId, WELCOME_MESSAGE);
                    // Add icons
                    message.replyMarkup(createIconsStage0());
                    sendMessage(message);
                }
            }
            // Process icon clicks
            else {
                SendMessage message = processIconClick(update);
                if (message != null) {
                    sendMessage(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(SendMessage message) {
        SendResponse response = telegramBot.execute(message);
        if (!response.isOk()) {
            logger.warn("Message was not sent: {}, error code: {}", message, response.errorCode());
        }

    }

    /**
     * Answer to welcome message (to "/start" command) - Stage 0
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createIconsStage0() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton iconStage1 = new InlineKeyboardButton(ICON_STAGE1_TEXT).callbackData(ACTION_STAGE1_ICON_CLICKED);
        InlineKeyboardButton iconStage2 = new InlineKeyboardButton(ICON_STAGE2_TEXT).callbackData(ACTION_STAGE2_ICON_CLICKED);
        InlineKeyboardButton iconStage3 = new InlineKeyboardButton(ICON_STAGE3_TEXT).callbackData(ACTION_STAGE3_ICON_CLICKED);
        InlineKeyboardButton iconCallVolunteer = new InlineKeyboardButton(ICON_VOLUNTEER_TEXT).callbackData(ACTION_VOLUNTEER_ICON_CLICKED);
        inlineKeyboardMarkup.addRow(iconStage1);
        inlineKeyboardMarkup.addRow(iconStage2);
        inlineKeyboardMarkup.addRow(iconStage3);
        inlineKeyboardMarkup.addRow(iconCallVolunteer);
        return inlineKeyboardMarkup;
    }

    /**
     * Process icon clicks from guests.
     * @param update shelter guest input (icon click, etc.)
     *               if icon is clicked - {@code callbackQuery.data()}
     * @return message to the guest
     * @see InlineKeyboardButton#callbackData()
     */
    private SendMessage processIconClick(Update update) {
        SendMessage message = null;
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            long chatId = callbackQuery.message().chat().id();
            switch (callbackQuery.data()) {
                case ACTION_STAGE1_ICON_CLICKED -> message = new SendMessage(chatId, ACTION_STAGE1_ICON_CLICKED); // запрос информации о приюте
                case ACTION_STAGE2_ICON_CLICKED -> message = new SendMessage(chatId, ACTION_STAGE2_ICON_CLICKED); // запрос информации о питомце
                case ACTION_STAGE3_ICON_CLICKED -> message = new SendMessage(chatId, ACTION_STAGE3_ICON_CLICKED); // отправка отчета о питомце
                case ACTION_VOLUNTEER_ICON_CLICKED -> message = new SendMessage(chatId, ACTION_VOLUNTEER_ICON_CLICKED); // позвать волонтера
            }
        }
        return message;
    }

        //if (callbackQuery != null) {
        //    long chatId = callbackQuery.message().chat().id();

        //    if (callbackQuery.data().equals(ACTION_STAGE1_ICON_CLICKED)) {
        //     message = new SendMessage(chatId, ACTION_STAGE1_ICON_CLICKED);
        //    }

        //    else if (callbackQuery.data().equals(ACTION_STAGE2_ICON_CLICKED)) {
        //        message = new SendMessage(chatId, ACTION_STAGE2_ICON_CLICKED);
        //    }

        //    else if (callbackQuery.data().equals(ACTION_STAGE3_ICON_CLICKED)) {
        //        message = new SendMessage(chatId, ACTION_STAGE3_ICON_CLICKED);
        //    }

        //    else if (callbackQuery.data().equals(ACTION_VOLUNTEER_ICON_CLICKED)) {
        //    message = new SendMessage(chatId, ACTION_VOLUNTEER_ICON_CLICKED);
        //    }
        // }
}
