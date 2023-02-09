package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;


public class CallbackQueryHandler {
    public BotApiMethod<?> processCallBackQuery(CallbackQuery buttonQuery, UsersController usersController) {
        String data = buttonQuery.getData();
        if (data.startsWith("GENDER")) {
            return getGender(buttonQuery, data.substring(7, 14), usersController);
        } else if (data.startsWith("REGISTRATION")) {
            return getRegistrationAnswer(buttonQuery, data.substring(12), usersController);
        } else if (data.startsWith("GOAL")) {
            return getGoalAnswer(buttonQuery, data.substring(4), usersController);
        }
        return null;
    }

    private BotApiMethod<?> getGoalAnswer(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateWasRegistered(userId, "yes");
        usersController.updateGoal(userId, answer);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        editMessageText.setText("Процесс регистрации завершен!" +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGoal());
        return editMessageText;
    }

    private BotApiMethod<?> getGender(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateWasRegistered(userId, "age");
        usersController.updateGender(userId, answer);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
        editMessageText.setText("Сколько вам лет?");
        return editMessageText;
    }

    private BotApiMethod<?> getRegistrationAnswer(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        EditMessageText editMessageText = new EditMessageText();
        if (answer.equals("Да ✅")) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId())
                    .getId(), "gender");
            editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
            editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"}), "GENDER_"));
            editMessageText.setText("Какого вы пола?");
        } else {
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId())
                    .getId(), "no registration");
            editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
            editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
            editMessageText.setText("ок");
        }
        return editMessageText;
    }

}
