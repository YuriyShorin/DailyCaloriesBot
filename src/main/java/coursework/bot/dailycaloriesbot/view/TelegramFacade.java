package coursework.bot.dailycaloriesbot.view;

import coursework.bot.dailycaloriesbot.view.handlers.CallbackQueryHandler;
import coursework.bot.dailycaloriesbot.view.handlers.CommandsHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {


    public BotApiMethod<?> handleUpdate(Update update) {// получен update от Telegram
        if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallBackQuery(callbackQuery);
        }
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith("/")) { // если получено команда (начинается с /)
            return processCommand(update);
        }
        return null;
    }

    private BotApiMethod<?> processCommand(Update update) { // функция, обрабатывающая полученную команду
        String messageText = update.getMessage().getText();
        CommandsHandler commandsHandler = new CommandsHandler(); // обработчик команд
        switch (messageText) {
            case "/start" -> {
                return commandsHandler.startCommandReceived(update); // если получена команда /start
            }
            case "/reg" -> {
                return commandsHandler.registrationCommandReceived(update); // если получена команда /reg
            }
            default -> {
                return commandsHandler.unknownCommandReceived(update); // получена неизвестная команда
            }
        }
    }
}
