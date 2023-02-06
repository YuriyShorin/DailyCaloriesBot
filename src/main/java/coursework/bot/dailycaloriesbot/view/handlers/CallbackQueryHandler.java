package coursework.bot.dailycaloriesbot.view.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallbackQueryHandler {
    public BotApiMethod<?> processCallBackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        Integer messageId = buttonQuery.getMessage().getMessageId();
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonQuery.getMessage().getReplyMarkup();
        if (data.startsWith("GENDER")) {
            return getGender(chatId, messageId, inlineKeyboardMarkup, data.substring(7));
        }
        return null;
    }

    private BotApiMethod<?> getGender(String chatId, Integer messageId, InlineKeyboardMarkup inlineKeyboardMarkup, String message) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        editMessageText.setText("Ваш пол: " + message);
        return editMessageText;
    }
}
