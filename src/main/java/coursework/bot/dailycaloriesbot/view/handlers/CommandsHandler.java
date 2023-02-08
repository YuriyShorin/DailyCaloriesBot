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

    public SendMessage unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }
}
