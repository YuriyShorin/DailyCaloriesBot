package coursework.bot.dailycaloriesbot.view;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.view.handlers.CallbackQueryHandler;
import coursework.bot.dailycaloriesbot.view.handlers.CommandsHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {
    @Autowired
    UsersController usersController;

    public BotApiMethod<?> handleUpdate(Update update) { // получен update от Telegram
        if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallBackQuery(callbackQuery, usersController);
        }
        if (update.hasMessage() && update.getMessage().hasText()) { // если получено сообщение
            String messageText = update.getMessage().getText();
            if (messageText.startsWith("/")) { // получена команда
                return processCommand(update, messageText, usersController);
            }
            String stageOfRegistration = usersController.getUserByTelegramId(update.getMessage().getFrom().getId()).getWasRegistered();
            if (stageOfRegistration.equals("yes") || stageOfRegistration.equals("no registration")) { // процесс регистрации завершен
                return processCommand(update, "/addProduct", usersController);
            } else if (stageOfRegistration.equals("age")) { // процесс регистрации на стадии возраста
                return processCommand(update, "age", usersController);
            } else if (stageOfRegistration.equals("weight")) { // процесс регистрации на стадии веса
                return processCommand(update, "weight", usersController);
            } else if (stageOfRegistration.equals("height")) { // процесс регистрации на стадии роста
                return processCommand(update, "height", usersController);
            } else {
                return processCommand(update, "goal", usersController);
            }
        }
        return null;
    }

    private BotApiMethod<?> processCommand(Update update, String command, UsersController usersController) { // функция, обрабатывающая полученную команду
        CommandsHandler commandsHandler = new CommandsHandler(); // обработчик команд
        switch (command) {
            case "/start" -> {
                return commandsHandler.startCommandReceived(update, usersController); // если получена команда /start
            }
            case "age" -> {
                return commandsHandler.ageCommandReceived(update, usersController);
            }
            case "weight" -> {
                return commandsHandler.weightCommandReceived(update, usersController);
            }
            case "height" -> {
                return commandsHandler.heightCommandReceived(update, usersController);
            }
            case "goal" -> {
                return commandsHandler.goalCommandReceived(update, usersController);
            }
            default -> {
                return commandsHandler.unknownCommandReceived(update); // получена неизвестная команда
            }
        }
    }
}
