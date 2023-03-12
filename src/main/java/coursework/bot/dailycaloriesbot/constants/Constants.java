package coursework.bot.dailycaloriesbot.constants;

import coursework.bot.dailycaloriesbot.entities.Products;
import coursework.bot.dailycaloriesbot.entities.Users;
import java.util.List;

public class Constants {

    public static final String YES = "Да ✅";
    public static final String CHANGE = "Изменить ⚙️";
    public static final String ADD = "Добавить ✅";
    public static final String HelloMessage = """
            @CalorieTrackingBot позволяет отслеживать потребленные за день калории и собирать статистику.
                                
            Регистрация позволит расчитать норму дневного потребления.
                             
            <b>Важно: Мы не храним персональные данные. Бот имеет доступ лишь к Telegram id.</b>

            Желаете зарегистрироваться?""";
    public static String WHAT_IS_YOUR_GENDER_QUESTION = "Какого вы пола?";
    public static String WHAT_IS_YOUR_AGE_QUESTION = "Сколько вам лет?";
    public static String WHAT_IS_YOUR_WEIGHT_QUESTION = "Какой у вас вес (кг)?";
    public static String WHAT_IS_YOUR_HEIGHT_QUESTION = "Какой у вас рост (см)?";
    public static String WHAT_IS_YOUR_GOAL_QUESTION = "Какова ваша цель отслеживания килокалорий?";
    public static String WHAT_IS_YOUR_ACTIVITY_QUESTION = """
            Какая у вас активность?

            <b>Минимальная</b> - сидячая работа, не требующая значительных физических нагрузок.
                        
            <b>Слабая</b> - интенсивные упражнения не менее 20 минут один-три раза в неделю.
                        
            <b>Умеренная</b> - интенсивная тренировка не менее 30-60 мин три-четыре раза в неделю.
                        
            <b>Тяжелая</b> - интенсивные упражнения и занятия спортом 5-7 дней в неделю или трудоемкая работа.
                        
            <b>Экстремальная</b> - занятия спортом с почти ежедневным графиком и несколькими тренировками в течение дня или очень трудоемкая работа.""";
    public static final List<String> YES_OR_NO_BUTTONS = List.of(new String[]{"Да ✅", "Нет ❌"});
    public static final List<String> YES_OR_CHANGE_BUTTONS = List.of(new String[]{"Да ✅", "Изменить ⚙️"});
    public static final List<String> SAVE_OR_CHANGE_BUTTONS = List.of(new String[]{"Сохранить ✅", "Изменить ⚙️"});
    public static final List<String> GENDER_BUTTONS = List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"});
    public static final List<String> GOAL_BUTTONS = List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"});
    public static final List<String> ACTIVITY_BUTTONS = List.of(new String[]{"Минимальная", "Слабая",
            "Умеренная", "Тяжелая", "Экстремальная"});
    public static final List<String> CHANGE_BUTTONS = List.of(new String[]{"Пол", "Возраст", "Вес", "Рост",
            "Цель", "Активность", "Пройти регистрацию заново"});
    public static final List<String> FINAL_KEYBOARD = List.of(new String[]{"\uD83C\uDF54 Добавить продукт",
            "\uD83D\uDCA7 Добавить стакан", " \uD83D\uDCCA Статистика", "⚙️ Изменить данные", "❓Помощь", "\uD83C\uDF71 Моя норма"});
    public static final List<String> ADD_OR_INFO_BUTTONS = List.of(new String[]{"Добавить ✅", "Не добавлять ❌"});

    public static String getIsAllRightMessage(Users user) {
        return "Ваши данные изменены." +
                "\n\nВаш пол: <b>" + user.getGender() +
                "</b>\nВаш возраст <b>: " + user.getAge() +
                "</b>\nВаш вес: <b>" + user.getWeight() +
                "</b>\nВаш рост: <b>" + user.getHeight() +
                "</b>\nВаша цель: <b>" + user.getGoal() +
                "</b>\nВаша активность <b>: " + user.getActivity() +
                "</b>\n\nВсе верно?";
    }

    public static String getAllDataMessage(Users user) {
        return "Ваш пол: <b>" + user.getGender() +
                "</b>\nВаш возраст <b>: " + user.getAge() +
                "</b>\nВаш вес: <b>" + user.getWeight() +
                "</b>\nВаш рост: <b>" + user.getHeight() +
                "</b>\nВаша цель: <b>" + user.getGoal() +
                "</b>\nВаша активность <b>: " + user.getActivity() + "</b>";
    }

    public static String getFormulaResultMessage(int result) {
        return "Результат был рассчитан по формуле " +
                "Миффлина — Сан-Жеора с учетом вашей активности и цели похудения.\n\n" +
                "Ваша норма ежедневного потребления: <b>" + result + " ккал</b>.";
    }

    public static String getProductAddedMessage(Products product) {
        return product.getProduct() +
                "\n\nКкал: <b>" + product.getKilocalories() +
                "</b>\nБелки: <b>" + product.getProteins() +
                "</b>\nЖиры: <b>" + product.getFats() +
                "</b>\nУглеводы: <b>" + product.getCarbohydrates() + "</b>";
    }

    public static String getUserIntakeMessage(Users user) {
        return "Потреблено калорий: <b>" + user.getDailyCalorieIntake() + "</b>.";
    }

    public static String getProductWontBeCountedMessage(Users user) {
        return "Продукт не будет учтен в потребленных калориях.\n\n" + getUserIntakeMessage(user);
    }
}