package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import java.util.List;


public class CallbackQueryHandler {
    public BotApiMethod<?> processCallBackQuery(CallbackQuery buttonQuery, UsersController usersController) {
        String data = buttonQuery.getData();
        if (data.startsWith("GENDER")) {
            return getGender(buttonQuery, data.substring(7), usersController);
        } else if (data.startsWith("REGISTRATION")) {
            return getRegistrationAnswer(buttonQuery, data.substring(12), usersController);
        }
        return null;
    }

    private SendMessage getGender(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId(), "age");
        return new SendMessage(buttonQuery.getMessage().getChatId().toString(), "Сколько вам лет?");
    }

    private SendMessage getRegistrationAnswer(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        SendMessage sendMessage;
        if (answer.equals("Да")) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel();
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId(), "gender");
            sendMessage = new SendMessage(buttonQuery.getMessage().getChatId().toString(), "Какого вы пола?");
            sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина", "Женщина"}), "GENDER_"));
        } else {
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId(), "no registration");
            sendMessage = new SendMessage(buttonQuery.getMessage().getChatId().toString(), "ок");
        }
        return sendMessage;
    }
}
