package pro.sky.telegrambot.constants;
import com.vdurmont.emoji.EmojiParser;


public class Constants {
    //Иконки

    public final static String ICON_SMILE = EmojiParser.parseToUnicode(":smile:");
    public final static String ICON_WAVE = EmojiParser.parseToUnicode(":wave:");

    // Cообщения
    public final static String WELCOME_MESSAGE = "Привет! " + ICON_WAVE + " Это бот для бездомных животных. Чем можем помочь? " + ICON_SMILE;
    public final static String NO_VOLUNTEERS_AVAILABLE = "Свободных волонтеров нет. Пожалуйста попробуйте попозже";

    // Текст кнопок
    public final static String ICON_STAGE1_TEXT = "Информация о приюте - этап 1";
    public final static String ICON_STAGE2_TEXT = "Взять питомца из приюта - этап 2";
    public final static String ICON_STAGE3_TEXT = "Прислать отчет о питомце - этап 3";
    public final static String ICON_VOLUNTEER_TEXT = "Вызвать волонтера";

    // Ответы на нажатые кнопки
    public final static String ACTION_STAGE1_ICON_CLICKED = "Кнопку 1 нажали";
    public final static String ACTION_STAGE2_ICON_CLICKED = "Кнопку 2 нажали";
    public final static String ACTION_STAGE3_ICON_CLICKED = "Кнопку 3 нажали";
    public final static String ACTION_VOLUNTEER_ICON_CLICKED = "Волонтера позвали";
}
