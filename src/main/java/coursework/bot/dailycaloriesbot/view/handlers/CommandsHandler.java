package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;


@Component
public class CommandsHandler {

    public SendMessage startCommandReceived(Update update, UsersController usersController) { // обработчик команды /start
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage;
        if (user == null) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Желаете ли зарегистрироваться?");
            sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Нет ❌"}), "REGISTRATION")); // добавление двух кнопок
            usersController.createUser(new Users(update.getMessage().getFrom().getId(), "no"));
        } else if (user.getWasRegistered().equals("yes")) {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "С возвращением!");
        } else {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Регистрация будет продолжена!");
        }
        return sendMessage;
    }

    public SendMessage ageCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        int age;
        try {
            age = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateAge(userId, age);
        usersController.updateWasRegistered(userId, "weight");
        return new SendMessage(update.getMessage().getChatId().toString(), "Какой у вас вес (кг)?");
    }

    public SendMessage weightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        double weight;
        try {
            weight = Double.parseDouble(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вес должен быть целым или дробным числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateWeight(userId, weight);
        usersController.updateWasRegistered(userId, "height");
        return new SendMessage(update.getMessage().getChatId().toString(), "Какой у вас рост (см)?");
    }

    public SendMessage heightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        double height;
        try {
            height = Double.parseDouble(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вес должен быть целым или дробным числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateHeight(userId, height);
        usersController.updateWasRegistered(userId, "goal");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Какова ваша цель похудения?");
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"}), "GOAL"));
        return sendMessage;
    }

    public SendMessage unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }

    public BotApiMethod<?> changeAgeCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        int age;
        try {
            age = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateAge(userId, age);
        usersController.updateWasRegistered(userId, "yes");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ваши данные изменены." +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGoal() +
                "\n\nВсе верно?");
        sendMessage.setChatId(update.getMessage().getChatId());
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }

    public BotApiMethod<?> changeHeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        double height;
        try {
            height = Double.parseDouble(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вес должен быть целым или дробным числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateHeight(userId, height);
        usersController.updateWasRegistered(userId, "yes");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ваши данные изменены." +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGoal() +
                "\n\nВсе верно?");
        sendMessage.setChatId(update.getMessage().getChatId());
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }

    public BotApiMethod<?> changeWeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        double weight;
        try {
            weight = Double.parseDouble(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вес должен быть целым или дробным числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateWeight(userId, weight);
        usersController.updateWasRegistered(userId, "yes");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ваши данные изменены." +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getGoal() +
                "\n\nВсе верно?");
        sendMessage.setChatId(update.getMessage().getChatId());
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }
}
