package coursework.bot.dailycaloriesbot.model.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsHandler {

    public SendMessage startCommandReceived(Update update) { // обработчик команды /start
        return new SendMessage(update.getMessage().getChatId().toString(), "Привет, " + update.getMessage().getFrom().getUserName());
    }

    public SendMessage unknownCommandReceived(Update update) { // обработчик неизвестной команды
        return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
    }
}
