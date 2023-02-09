package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;


@Component
public class CommandsHandler {

    public SendMessage startCommandReceived(Update update, UsersController usersController) { // обработчик команды /start
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage;
        if (user == null) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel();
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Желаете ли зарегистрироваться?");
            sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да", "Нет"}), "REGISTRATION")); // добавление двух кнопок
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
        return new SendMessage(update.getMessage().getChatId().toString(), "Сколько килокалорий вы хотите потреблять за день?");
    }

    public SendMessage goalCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        int goal;
        try {
            goal = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Цель должна быть целым числом");
        }
        long userId = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getId();
        usersController.updateGoal(userId, goal);
        usersController.updateWasRegistered(userId, "yes");
        return new SendMessage(update.getMessage().getChatId().toString(), "Процесс регистрации завершен!");
    }

    public SendMessage unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }
}
