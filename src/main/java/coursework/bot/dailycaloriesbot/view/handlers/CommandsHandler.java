package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import coursework.bot.dailycaloriesbot.view.keyboards.ReplyKeyboardModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
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
        return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
    }

    public SendMessage registrationCommandReceived(Update update) {
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel();
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),"Какой у вас пол?");
        List<String> list = new ArrayList<>();
        list.add("Мужчина");
        list.add("Женщина");
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(list));
        return sendMessage;
    }
}
