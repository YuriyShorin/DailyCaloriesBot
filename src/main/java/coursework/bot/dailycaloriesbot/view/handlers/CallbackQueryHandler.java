package coursework.bot.dailycaloriesbot.view.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackQueryHandler {
    public BotApiMethod<?> processCallBackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        if (data.startsWith("GENDER")) {
            return getGender(chatId, data.substring(7));
        }
        return null;
    }

    private SendMessage getGender(String chatId, String message) {
        return new SendMessage(chatId, "Ваш пол: " + message);
    }
}
