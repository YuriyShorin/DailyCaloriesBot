package coursework.bot.dailycaloriesbot.constants;

import coursework.bot.dailycaloriesbot.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Constants {

    public static final String YES = "Да ✅";
    public static final String CHANGE = "Изменить ⚙️";

    public static final String CHANGE_GRAMS = "Изменить граммовку";
    public static final String ADD = "Добавить ✅";
    public static final String MORE = "Еще товары \uD83C\uDF55";
    public static final String FAVOURITES = "Избранное ⭐️";
    public static final String RECENT = "Недавние \uD83D\uDDD3️";
    public static final String ADD_TO_FAVOURITES = "В избранное ⭐️";
    public static final String DELETE_FROM_FAVOURITES = "Удалить из избранного";
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
    public static final List<String> STATS_BUTTONS = List.of(new String[]{"День", "Неделя", "Месяц", "За все время", "Вся статистика"});
    public static final List<String> FINAL_KEYBOARD = List.of(new String[]{"\uD83C\uDF54 Добавить продукт",
            "\uD83D\uDCA7 Добавить стакан", " \uD83D\uDCCA Статистика", "⚙️ Изменить данные", "❓Помощь", "\uD83C\uDF71 Моя норма"});
    public static List<String> ADD_PRODUCT_BUTTON = List.of(new String[]{"Избранное ⭐️", "Недавние \uD83D\uDDD3️", "Отменить ❌"});
    public static final List<String> ADD_PRODUCT_WITH_ADD_TO_FAVOURITES_BUTTONS = List.of(new String[]{"Добавить ✅",
            "Еще товары \uD83C\uDF55", "В избранное ⭐️", "Изменить граммовку", "Не добавлять ❌"});
    public static final List<String> ADD_PRODUCT_WITH_DELETE_FROM_FAVOURITES_BUTTONS = List.of(new String[]{"Добавить ✅",
            "Еще товары \uD83C\uDF55", "Удалить из избранного", "Изменить граммовку", "Не добавлять ❌"});


    public static String getIsAllRightMessage(UsersRegistrationData user) {
        return "Ваши данные изменены." +
                "\n\nВаш пол: <b>" + user.getGender() +
                "</b>\nВаш возраст <b>: " + user.getAge() +
                "</b>\nВаш вес: <b>" + user.getWeight() +
                "</b>\nВаш рост: <b>" + user.getHeight() +
                "</b>\nВаша цель: <b>" + user.getGoal() +
                "</b>\nВаша активность <b>: " + user.getActivity() +
                "</b>\n\nВсе верно?";
    }

    public static String getAllDataMessage(UsersRegistrationData user) {
        return "Ваш пол: <b>" + user.getGender() +
                "</b>\nВаш возраст <b>: " + user.getAge() +
                "</b>\nВаш вес: <b>" + user.getWeight() +
                "</b>\nВаш рост: <b>" + user.getHeight() +
                "</b>\nВаша цель: <b>" + user.getGoal() +
                "</b>\nВаша активность <b>: " + user.getActivity() + "</b>";
    }

    public static String getFormulaResultMessage(double calories) {
        return "Результат был рассчитан по формуле " +
                "Миффлина — Сан-Жеора с учетом вашей активности и цели похудения.\n\n" +
                "Ваша норма ежедневного потребления: " +
                "\nКкал: <b>" + (int) calories + "</b>" +
                "\nБелки: <b>" + (int) (calories * 0.3 / 4) + "г</b>" +
                "\nЖиры: <b>" + (int) (calories * 0.3 / 9) + "г</b>" +
                "\nУглероды: <b>" + (int) (calories * 0.4 / 4) + "г</b>";
    }

    public static String getProductAddedMessage(Products product, double coefficient) {
        return product.getProduct() + ", " + (int) (coefficient * 100) + " грамм." +
                "\n\nКкал: <b>" + String.format(Locale.US, "%.2f", product.getKilocalories() * coefficient) +
                "</b>\nБелки: <b>" + String.format(Locale.US, "%.2f", product.getProteins() * coefficient) +
                "</b>\nЖиры: <b>" + String.format(Locale.US, "%.2f", product.getFats() * coefficient) +
                "</b>\nУглеводы: <b>" + String.format(Locale.US, "%.2f", product.getCarbohydrates() * coefficient) + "</b>";
    }

    public static String getUserDailyIntakeMessage(UsersStatistics user) {
        return "Потреблено за день:\n\n" +
                "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getDailyCalorieIntake()) + "</b>\n" +
                "Белков: <b>" + String.format(Locale.US, "%.1f", user.getDailyProteinsIntake()) + "</b>\n" +
                "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getDailyFatsIntake()) + "</b>\n" +
                "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getDailyCarbohydratesIntake()) + "</b>\n" +
                "Выпито стаканов воды: <b>" + user.getDailyGlassesOfWater() + "</b>";
    }

    public static String getUserWeeklyIntakeMessage(UsersStatistics user) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        int daysInBot = user.getDaysInBot();
        if (daysInBot < dayOfWeek) {
            return "Потреблено в среднем за неделю:\n\n" +
                    "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyCalorieIntake() / daysInBot) + "</b>\n" +
                    "Белков: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyProteinsIntake() / daysInBot) + "</b>\n" +
                    "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyFatsIntake() / daysInBot) + "</b>\n" +
                    "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyCarbohydratesIntake() / daysInBot) + "</b>\n" +
                    "Выпито стаканов воды: <b>" + String.format(Locale.US, "%.1f", (double) user.getMonthlyGlassesOfWater() / daysInBot) + "</b>";
        }
        return "Потреблено в среднем за неделю:\n\n" +
                "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyCalorieIntake() / dayOfWeek) + "</b>\n" +
                "Белков: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyProteinsIntake() / dayOfWeek) + "</b>\n" +
                "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyFatsIntake() / dayOfWeek) + "</b>\n" +
                "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getWeeklyCarbohydratesIntake() / dayOfWeek) + "</b>\n" +
                "Выпито стаканов воды: <b>" + String.format(Locale.US, "%.1f", (double) user.getMonthlyGlassesOfWater() / dayOfWeek) + "</b>";
    }

    public static String getUserMonthlyIntakeMessage(UsersStatistics user) {
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        int daysInBot = user.getDaysInBot();
        if (daysInBot < dayOfMonth) {
            return "Потреблено в среднем за месяц:\n\n" +
                    "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyCalorieIntake() / daysInBot) + "</b>\n" +
                    "Белков: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyProteinsIntake() / daysInBot) + "</b>\n" +
                    "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyFatsIntake() / daysInBot) + "</b>\n" +
                    "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyCarbohydratesIntake() / daysInBot) + "</b>\n" +
                    "Выпито стаканов воды: <b>" + String.format(Locale.US, "%.1f", (double) user.getMonthlyGlassesOfWater() / daysInBot) + "</b>";
        }
        return "Потреблено в среднем за месяц:\n\n" +
                "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyCalorieIntake() / dayOfMonth) + "</b>\n" +
                "Белков: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyProteinsIntake() / dayOfMonth) + "</b>\n" +
                "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyFatsIntake() / dayOfMonth) + "</b>\n" +
                "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getMonthlyCarbohydratesIntake() / dayOfMonth) + "</b>\n" +
                "Выпито стаканов воды: <b>" + String.format(Locale.US, "%.1f", (double) user.getMonthlyGlassesOfWater() / dayOfMonth) + "</b>";
    }

    public static String getUserAllTimeIntakeMessage(UsersStatistics user) {
        return "Потреблено в среднем за все время:\n\n" +
                "Ккал: <b>" + String.format(Locale.US, "%.1f", user.getAllTimeCalorieIntake() / user.getDaysInBot()) + "</b>\n" +
                "Белков: <b>" + String.format(Locale.US, "%.1f", user.getAllTimeProteinsIntake() / user.getDaysInBot()) + "</b>\n" +
                "Жиров: <b>" + String.format(Locale.US, "%.1f", user.getAllTimeFatsIntake() / user.getDaysInBot()) + "</b>\n" +
                "Углеводов: <b>" + String.format(Locale.US, "%.1f", user.getAllTimeCarbohydratesIntake() / user.getDaysInBot()) + "</b>\n" +
                "Выпито стаканов воды: <b>" + String.format(Locale.US, "%.1f", (double) user.getAllTimeGlassesOfWater() / user.getDaysInBot()) + "</b>";
    }

    public static String getUserAllStatsMessage(UsersStatistics user) {
        return getUserDailyIntakeMessage(user) + "\n\n" + getUserWeeklyIntakeMessage(user) + "\n\n" +
                getUserMonthlyIntakeMessage(user) + "\n\n" + getUserAllTimeIntakeMessage(user);
    }

    public static String getProductWontBeCountedMessage(UsersStatistics user) {
        return "Продукт не будет учтен в потребленных калориях.\n\n" + getUserDailyIntakeMessage(user);
    }

    public static String getNumberOfProductsInFavouritesMessage(UsersFavourites usersFavourites) {
        Set<Favourites> favourites = usersFavourites.getFavourites();
        if (favourites.size() == 20) {
            return "В избранном максимальное число продуктов: <b>20</b>";
        }
        return "Количество продуктов в избранном: <b>" + favourites.size() + "</b>";
    }
}