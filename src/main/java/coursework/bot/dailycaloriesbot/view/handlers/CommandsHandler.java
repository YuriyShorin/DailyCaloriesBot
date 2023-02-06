package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import coursework.bot.dailycaloriesbot.view.keyboards.ReplyKeyboardModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class CommandsHandler {


    public SendMessage startCommandReceived(Update update) { // обработчик команды /start
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Привет, " + update.getMessage().getFrom().getUserName());
        sendMessage.setReplyMarkup(replyKeyboardModel.getStartMenuKeyboard());
        return sendMessage;
    }

    public SendMessage unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }

    public SendMessage registrationCommandReceived(Update update) {
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel();
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Какой у вас пол?");
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина", "Женщина"}), "GENDER_")); // добавление двух кнопок
        return sendMessage;
    }
}
