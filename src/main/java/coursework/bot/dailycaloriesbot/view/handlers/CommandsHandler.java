package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.utills.NumbersUtil;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import coursework.bot.dailycaloriesbot.view.keyboards.ReplyKeyboardModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.List;

@Component
public class CommandsHandler {

    public BotApiMethod<?> startCommandReceived(Update update, UsersController usersController) { // обработчик команды /start
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage;
        if (user == null) {
            sendMessage = createRegistrationYesOrNoMessage("""
                    @CalorieTrackingBot позволяет отслеживать потребленные за день калории и собирать статистику.
                    
                    Регистрация позволит расчитать норму дневного потребления.
                 
                    <b>Важно: Мы не храним персональные данные. Бот имеет доступ лишь к Telegram id.</b>

                    Желаете зарегистрироваться?""", "REGISTRATION", update.getMessage()
                    .getChatId().toString());
            usersController.createUser(new Users(update.getMessage().getFrom().getId(), "no"));
        } else if (user.getWasRegistered().equals("no")) {
            sendMessage = createRegistrationYesOrNoMessage("""
                    @CalorieTrackingBot позволяет отслеживать потребленные за день калории и собирать статистику.
                    
                    Регистрация позволит расчитать норму дневного потребления.
                 
                    <b>Важно: Мы не храним персональные данные. Бот имеет доступ лишь к Telegram id.</b>

                    Желаете зарегистрироваться?""", "REGISTRATION", update.getMessage()
                    .getChatId().toString());
        } else if (user.getWasRegistered().equals("yes") || user.getWasRegistered().equals("no registration")) {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "С возвращением!");
            ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
            ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardModel.getReplyKeyboardMarkup(List.of(new String[]{"\uD83C\uDF54 Добавить продукт",
                    "\uD83D\uDCA7 Добавить стакан", " \uD83D\uDCCA Статистика", "⚙️ Изменить данные", "❓Помощь", "\uD83C\uDF71 Моя норма"}), 2);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        } else {
            sendMessage = createRegistrationYesOrNoMessage("Хотели бы продолжить регистрацию?", "WAS_REGISTRATION_CONTINUED", update.getMessage()
                    .getChatId().toString());
        }
        return sendMessage;
    }

    public BotApiMethod<?> ageCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Integer age = NumbersUtil.parseInt(messageText);
        if (age == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        if (!NumbersUtil.checkAge(age)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный возраст: " + age);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateAge(userId, age);
        usersController.updateWasRegistered(userId, "weight");
        return new SendMessage(update.getMessage().getChatId().toString(), "Какой у вас вес (кг)?");
    }

    public BotApiMethod<?> weightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double weight = NumbersUtil.parseDouble(messageText);
        if (weight == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Вес должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkWeight(weight)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный вес: " + weight);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateWeight(userId, weight);
        usersController.updateWasRegistered(userId, "height");
        return new SendMessage(update.getMessage().getChatId().toString(), "Какой у вас рост (см)?");
    }

    public BotApiMethod<?> heightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double height = NumbersUtil.parseDouble(messageText);
        if (height == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Рост должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkHeight(height)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный рост: " + height);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateHeight(userId, height);
        usersController.updateWasRegistered(userId, "goal");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId()
                .toString(), "Какова ваша цель отслеживания килокалорий?");
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"}), "GOAL"));
        return sendMessage;
    }

    public BotApiMethod<?> changeAgeCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Integer age = NumbersUtil.parseInt(messageText);
        if (age == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        if (!NumbersUtil.checkAge(age)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный возраст: " + age);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateAge(userId, age);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> changeWeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double weight = NumbersUtil.parseDouble(messageText);
        if (weight == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Вес должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkWeight(weight)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный вес: " + weight);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateWeight(userId, weight);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> changeHeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double height = NumbersUtil.parseDouble(messageText);
        if (height == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Рост должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkHeight(height)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный рост: " + height);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateHeight(userId, height);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> continueCommandReceived(Update update) {
        return createFinalKeyboard(update);
    }

    public BotApiMethod<?> addProductCommandReceived(Update update, UsersController usersController) {
        return new SendMessage(update.getMessage().getChatId().toString(), "addProductCommandReceived");
    }

    public BotApiMethod<?> addGlassOfWaterCommandReceived(Update update, UsersController usersController) {
        long userId = update.getMessage().getFrom().getId();
        usersController.incrementGlassesOfWater(userId);
        return new SendMessage(String.valueOf(userId), "За день было выпито: " + usersController.getUserByTelegramId(userId).getGlassesOfWater());
    }

    public BotApiMethod<?> getStatisticsCommandReceived(Update update, UsersController usersController) {
        return new SendMessage(update.getMessage().getChatId().toString(), "getStatisticsCommandReceived");
    }

    public BotApiMethod<?> changeDataCommandReceived(Update update, UsersController usersController) {
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText("\n\nВаш пол: <b>" + user.getGender() +
                        "</b>\nВаш возраст <b>: " + user.getAge() +
                        "</b>\nВаш вес: <b>" + user.getWeight() +
                        "</b>\nВаш рост: <b>" + user.getHeight() +
                        "</b>\nВаша цель: <b>" + user.getGoal() +
                        "</b>\nВаша активность <b>: " + user.getActivity() + "</b>");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Сохранить ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }

    public BotApiMethod<?> getHelpCommandReceived(Update update, UsersController usersController) {
        return new SendMessage(update.getMessage().getChatId().toString(), "getHelpCommandReceived");
    }

    public BotApiMethod<?> getNormCommandReceived(Update update, UsersController usersController) {
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        if (user.getWasRegistered().equals("no registration")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Необходимо зарегистрироваться для расчета формулы");
        }
        double result;
        if (user.getGender().equals("Мужчина")) {
            result = 9.99 * user.getWeight() + 6.25 * user.getHeight() - 4.92 * user.getAge() + 5;
        } else {
            result = 9.99 * user.getWeight() + 6.25 * user.getHeight() - 4.92 * user.getAge() - 161;
        }
        switch (user.getActivity()) {
            case "Минимальная" -> result *= 1.2;
            case "Слабая" -> result *= 1.375;
            case "Умеренная" -> result *= 1.55;
            case "Тяжелая" -> result *= 1.7;
            case "Экстремальная" -> result *= 1.9;
            default -> result *= 1;
        }
        if (user.getGoal().equals("Похудение")) {
            result *= 0.8;
        }
        if (user.getGoal().equals("Набор массы")) {
            result *= 1.2;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText("Результат был рассчитан по формуле " +
                "Миффлина — Сан-Жеора с учетом вашей активности и цели похудения.\n\n" +
                "Ваша норма ежедневного потребления: <b>" + (int) result + " ккал</b>");
        return sendMessage;
    }

    public BotApiMethod<?> unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }

    private BotApiMethod<?> createFinalRegistrationMessage(long userId, UsersController usersController) {
        usersController.updateWasRegistered(userId, "yes");
        Users user = usersController.getUserByTelegramId(userId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(userId));
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText("Ваши данные изменены." +
                "\n\nВаш пол: <b>" + user.getGender() +
                "</b>\nВаш возраст <b>: " + user.getAge() +
                "</b>\nВаш вес: <b>" + user.getWeight() +
                "</b>\nВаш рост: <b>" + user.getHeight() +
                "</b>\nВаша цель: <b>" + user.getGoal() +
                "</b>\nВаша активность <b>: " + user.getActivity() +
                "</b>\n\nВсе верно?");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }

    private BotApiMethod <?> createFinalKeyboard(Update update){
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Вам доступен основной функционал бота");
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardModel.getReplyKeyboardMarkup(List.of(new String[]{"\uD83C\uDF54 Добавить продукт",
                "\uD83D\uDCA7 Добавить стакан", " \uD83D\uDCCA Статистика", "⚙️ Изменить данные", "❓Помощь", "\uD83C\uDF71 Моя норма"}), 2);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
    private SendMessage createRegistrationYesOrNoMessage(String messageText, String callbackData, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(messageText);
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Нет ❌"}), callbackData));
        return sendMessage;
    }
}