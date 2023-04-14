package pro.sky.telegrambot.constants;

import com.vdurmont.emoji.EmojiParser;


public class Constants {
    //Иконки

    public final static String ICON_SMILE = EmojiParser.parseToUnicode(":smile:");
    public final static String ICON_WAVE = EmojiParser.parseToUnicode(":wave:");
    public final static String ICON_DOG = EmojiParser.parseToUnicode(":dog:");
    public final static String ICON_CAT = EmojiParser.parseToUnicode(":cat:");
    public final static String ICON_BIRD = EmojiParser.parseToUnicode(":bird:");

    public final static String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";



    // Cообщения
    public final static String WELCOME_MESSAGE = "Привет! " + ICON_WAVE + " Это бот для бездомных животных. Чем можем помочь? " + ICON_SMILE;
    public final static String NO_VOLUNTEERS_AVAILABLE = "Свободных волонтеров нет. Пожалуйста попробуйте попозже";
    public final static String ICON_BECOME_ADOPTER_INSTRUCTIONS = "Инструкции - стать усыновителем";
    public final static String CLICKED_ICON_BECOME_ADOPTER_INSTRUCTIONS = "Инструкции усыновителя";

    public final static String ICON_ENTER_ADOPTER_DETAILS = "Стать усыновителем";
    public final static String CLICKED_ICON_ENTER_ADOPTER_DETAILS = "Кнопку нажали";

    public final static String ADOPTER_SAVE_INSTRUCTION = "При нажатии кнопки \"Стать усыновителем\" Вам необходимо будет направить семь (7) сообщений (одно за другим) \n " +
            "Первое сообщение - свое имя. \n" +
            "Второе сообщение - свою фамилию. \n" +
            "Третье сообщение - номер своего паспорта. \n" +
            "Четвертое сообщение - свой возраст. \n" +
            "Пятое сообщение - свой номер телефона. \n" +
            "Шестое сообщение - свой е-мейл. \n" +
            "Седьмое сообщение - номер выбранного Вами незанятого питомца. \n" +
            "После отправки этих 7-ми сообщений ваша учетная запись будет создана, питомец закреплен за Вами \n" +
            "- о чем Вы получите соответствующее подтверждение\n";
    public final static String REQUEST_FIRST_NAME = "Пожалуйста, введите свое имя";
    public final static String SAVED_FIRST_NAME = "Имя сохранено. Пожалуйста, введите свою фамилию";
    public final static String SAVED_LAST_NAME = "Фамилия сохранена. Пожалуйста, введите номер своего паспорта";
    public final static String SAVED_PASSPORT = "Паспорт сохранен. Пожалуйста, введите свой возраст";
    public final static String SAVED_AGE = "Возраст сохранен. Пожалуйста, введите номер своего телефона";
    public final static String SAVED_PHONE = "Номер телефона сохранен. Пожалуйста, введите свой е-мейл";
    public final static String SAVED_EMAIL = "Е-мейл сохранен. Пожалуйста, введите Id (номер) выбранного питомца";
    public final static String SUCCESSFUL_ADOPTER = "Забронировали за Вами питомца.\n" +
            " Проверяем данные, вернемся с ответом. Ваша учетная запись и присвоенный номер следующий:\n";





    public final static String SAVED_PHOTO_MESSAGE = "Фотография сохранена";
    public final static String SAVED_DIET_MESSAGE = "Рацион сохранен";
    public final static String SAVED_LIFE_STATUS_MESSAGE = "Самочувствие сохранено";
    public final static String SAVED_BEHAVIOR_MESSAGE = "Поведение сохранено";
    public final static String PET_REPORT_INSTRUCTION = "При нажатии кнопки \"Отправить отчет о питомце\" Вам необходимо будет направить четыре сообщениями (одно за другим) \n " +
            "Первое сообщение - отправить фото питомца. \n" +
            "Второе сообщение - рацион питомца. \n" +
            "Третье сообщение - опишите самочувствие питомца. \n" +
            "Четвертое сообщение - опишите поведение питомца, изменения отмеченные вами. \n \n " +
            "После отправки этих 4-х сообщений отчет будет успешно сохранен о чем Вы получите соответствующее подтверждение\n";

    public final static String LOAD_PHOTO_MESSAGE = "Пожалуйста, загрузите фотографию питомца";
    public final static String PET_REPORT_EXIST = "Сегодняшний отчет уже есть в системе";

    // Текст кнопок
    // Стадия 1:
    public final static String ICON_STAGE1_TEXT = "Информация о приюте";
    // Кнопки стадии 1:
    public final static String ICON_SHELTER_INFO_TEXT = "Кто мы?";
    public final static String ICON_SHELTER_CONTACT_DETAILS = "Контактная информация";
    public final static String ICON_SAFETY_SHELTER_RULES = "Правила приюта";
    public final static String ICON_LEAVE_CONTACT_DETAILS = "Оставить контакты";

    // Стадия 2:
    public final static String ICON_STAGE2_TEXT = "Как взять питомца из приюта";
    // Кнопки стадии 2:
    public final static String ICON_PETS_SELECT_DOG = "Вас интересуют собаки или кошки?";

    public final static String ICON_SELECT_DOG_AND_BECOME_ADOPTER = "Хотите стать усыновителем?";
    public final static String ICON_PETS_GET_TO_KNOW_RULES = "Знакомство с питомцем";
    public final static String ICON_PETS_DOCS_SET = "Документы по усыновлению";
    public final static String ICON_PETS_TRANSPORTATION_RULES = "Транспортировка питомца";
    public final static String ICON_SMALL_PETS_HOME_RECOMMENDATIONS = "Дом для щенка";
    public final static String ICON_BIG_PETS_HOME_RECOMMENDATIONS = "Дом для взростлой собаки";
    public final static String ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS = "Дом для питомца инвалида";
    public final static String ICON_PETS_SPECIALIST_ADVICE = "Советы Кинолога";
    public final static String ICON_PETS_SPECIALIST_CONTACTS = "Контакты Кинологов";
    public final static String ICON_PETS_REFUSAL_REASONS = "Причины возможных отказов";

    public final static String ICON_ADOPTER_DETAILS = "Данные усыновителя";

    // Стадия 3:
    public final static String ICON_STAGE3_TEXT = "Ежедневный отчет о питомце";
    // Кнопки стадии 3:
   public final static String ICON_PETS_REPORT_INSTRUCTIONS = "Инструкции к отчету о питомце";
   public final static String ICON_PETS_REPORT_SEND = "Отправить отчет о питомце";

    // Волонтер:
    public final static String ICON_VOLUNTEER_TEXT = "Позвать волонтера";

    //===========================================================================
    // Ответы на нажатые кнопки
    public final static String ACTION_STAGE1_ICON_CLICKED = "Информация о приюте, разделы:";
    // Ответы на кнопки стадии 1:
    public final static String CLICKED_ICON_SHELTER_INFO_TEXT = "Мы - ";
    public final static String CLICKED_ICON_SHELTER_CONTACT_DETAILS = "Информация о приюте:";
    public final static String CLICKED_ICON_SAFETY_SHELTER_RULES = "Правила приюта:";
    public final static String CLICKED_ICON_LEAVE_CONTACT_DETAILS = "Оставьте свои контактные данные";
    public final static String BUTTON_SHARE_CONTACT_DETAILS = "Отправить контакты";

    /*
    public final static String BUTTON_SHARE_CONTACT_DETAILS_AND_BECOME_ADOPTER = "Дать контакты и стать усыновителем";

    public final static String PROVIDE_PASSPORT = "Пожалуйста, введите номер Вашего паспорта в формате (серия номер) xxxx xxxxxx";
    public final static String PASSPORT_OK = "Паспорт сохранен, пожалуйста, введите Ваш возраст";
    public final static String WRONG_PASSPORT = "Некорректный формат паспорта - пожалуйста убедитесь, \n" +
            "что вводите его в формате (серия номер) xxxx xxxxxx\n";
    public final static String PROVIDE_EMAIL = "Ваш возраст записали. Пожалуйста, введите ваш е-мейл";
    public final static String EMAIL_OK = "Е-мейл сохранили. Пожалуйста, введите номер (id) выбранного Вами питомца";
     */
    public final static String WRONG_EMAIL = "Некорректный формат электронной почты. \n" +
            "Пожалуйста, убедитесь, что в предоставляемый Вами адрес соответствует формату xxxx@xxx.ru(.com или .org)\n";

        public final static String WRONG_PET = "Что-то пошло не так. Или выбранный Вами питомец уже занят, или такого id питомца нет.\n" +
            "Пожалуйста, убедитесь что Вы ввели id питомца в виде целого числа, без текста и знаков после запятой!\n";

    public final static String ADOPTER_EXIST = "Вы уже сохранены как усыновитель в нашей базе данных.\n" +
            " Брать второго питомца не разрешается. Ваш номер усыновителя, присвоенный Вам в прошлый раз, следующий:\n";


    // Ответы на кнопки стадии 2:
    public final static String ACTION_STAGE2_ICON_CLICKED = "Правила и выбор питомца:";

    public final static String CLICKED_ICON_PETS_SELECT_DOG = "Вот такие есть собаки:";
    public final static String CLICKED_ICON_SELECT_DOG_AND_BECOME_ADOPTER = "Стать усыновителем";
    public final static String CLICKED_ICON_PETS_SELECT_CAT = "Кошек пока нет, только собаки";
    public final static String CLICKED_ICON_PETS_GET_TO_KNOW_RULES = "Как знакомиться с питомцем";
    public final static String CLICKED_ICON_PETS_DOCS_SET = "Список документов";
    public final static String CLICKED_ICON_PETS_TRANSPORTATION_RULES = "Правила перевозки питомцев";
    public final static String CLICKED_ICON_SMALL_PETS_HOME_RECOMMENDATIONS = "Как подготовить дом для щенка";
    public final static String CLICKED_ICON_BIG_PETS_HOME_RECOMMENDATIONS = "Дом взрослой собаке";
    public final static String CLICKED_ICON_HANDICAPPED_PETS_HOME_RECOMMENDATIONS = "Дом питомцу-инвалиду";
    public final static String CLICKED_ICON_PETS_SPECIALIST_ADVICE = "Советы Кинолога";
    public final static String CLICKED_ICON_PETS_SPECIALIST_CONTACTS = "Контакты Кинологов";
    public final static String CLICKED_ICON_PETS_REFUSAL_REASONS = "Причины возможных отказов";

    public final static String CLICKED_ICON_ADOPTER_DETAILS = "Данные усыновителя";

    // Ответы на кнопки стадии 2:
    public final static String ACTION_STAGE3_ICON_CLICKED = "Ежедневный отчет о питомце";
    public final static String CLICKED_ICON_PETS_REPORT_SEND = "Кнопку 3 нажали";
    public final static String ICON_PETS_REPORT_INSTRUCTIONS_CLICKED = "Инструкции к отчету";
    public final static String ACTION_VOLUNTEER_ICON_CLICKED = "Волонтера позвали";


}
