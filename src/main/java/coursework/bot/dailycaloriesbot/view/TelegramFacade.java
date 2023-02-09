package coursework.bot.dailycaloriesbot.view;

import coursework.bot.dailycaloriesbot.controller.UsersController;
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
            String stageOfRegistration = usersController.getUserByTelegramId(update.getMessage().getFrom().getId())
                    .getWasRegistered();
            return switch (stageOfRegistration) {
                case "yes", "no registration" ->
                        processCommand(update, "/addProduct", usersController); // процесс регистрации завершен
                case "age" -> processCommand(update, "age", usersController); // процесс регистрации на стадии возраста
                case "weight" ->
                        processCommand(update, "weight", usersController); // процесс регистрации на стадии веса
                case "height" ->
                        processCommand(update, "height", usersController); // процесс регистрации на стадии роста
                default -> processCommand(update, "wrong", usersController);
            };
        }
        return null;
    }

    private BotApiMethod<?> processCommand(Update update, String command, UsersController usersController) { // функция, обрабатывающая полученную команду
        CommandsHandler commandsHandler = new CommandsHandler(); // обработчик команд
        return switch (command) {
            case "/start" ->
                    commandsHandler.startCommandReceived(update, usersController); // если получена команда /start
            case "age" -> commandsHandler.ageCommandReceived(update, usersController);
            case "weight" -> commandsHandler.weightCommandReceived(update, usersController);
            case "height" -> commandsHandler.heightCommandReceived(update, usersController);
            default -> commandsHandler.unknownCommandReceived(update); // получена неизвестная команда
        };
    }
}
