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
        } else if (data.startsWith("IS_REGISTRATION_CORRECT")) {
            return checkIsRegistrationCorrect(buttonQuery, data.substring(23));
        } else if (data.startsWith("CHANGE")) {
            return changeData(buttonQuery, data, usersController);
        } else if (data.startsWith("change_GENDER")) {
            return changeGender(buttonQuery, data.substring(13, 20), usersController);
        } else if (data.startsWith("change_GOAL")) {
            return changeGOAL(buttonQuery, data.substring(11), usersController);
        }
        return null;
    }

    private BotApiMethod<?> changeGOAL(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateGoal(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        editMessageText.setText("Ваши данные изменены." +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGoal() +
                "\n\n Все верно?");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return editMessageText;
    }

    private BotApiMethod<?> changeGender(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateGender(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        editMessageText.setText("Ваши данные изменены." +
                "\n\nВаш пол: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGender() +
                "\nВаш возраст: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getAge() +
                "\nВаш вес: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getWeight() +
                "\nВаш рост: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getHeight() +
                "\nВаша цель: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGoal() +
                "\n\n Все верно?");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
        return editMessageText;
    }

    private BotApiMethod<?> changeData(CallbackQuery buttonQuery, String data, UsersController usersController) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        if (data.endsWith("Пол")) {
            usersController.updateWasRegistered(userId, "change_gender");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText.setText("Какого вы пола?");
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"}), "change_GENDER"));
        } else if (data.endsWith("Возраст")) {
            usersController.updateWasRegistered(userId, "change_age");
            editMessageText.setText("Сколько вам лет?");
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
        } else if (data.endsWith("Рост")) {
            usersController.updateWasRegistered(userId, "change_height");
            editMessageText.setText("Какой у вас рост (см)?");
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
        } else if (data.endsWith("Вес")) {
            usersController.updateWasRegistered(userId, "change_weight");
            editMessageText.setText("Какой у вас вес (кг)?");
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
        } else {
            usersController.updateWasRegistered(userId, "change_goal");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText.setText("Какова ваша цель похудения?");
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"}), "change_GOAL"));
        }
        return editMessageText;
    }

    private BotApiMethod<?> checkIsRegistrationCorrect(CallbackQuery buttonQuery, String answer) {
        EditMessageText editMessageText = new EditMessageText();
        if (answer.equals("Да ✅")) {
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
            editMessageText.setChatId(buttonQuery.getMessage().getChatId());
            editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
            editMessageText.setText("Ваши данные сохранены");
        } else {
            editMessageText.setText("Что вы хотите изменить?");
            editMessageText.setChatId(buttonQuery.getMessage().getChatId());
            editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Пол", "Возраст", "Вес", "Рост", "Цель"}), "CHANGE"));
        }
        return editMessageText;
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
                "\nВаша цель: " + usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getGoal() +
                "\n\nВсе верно?");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Изменить ⚙️"}), "IS_REGISTRATION_CORRECT"));
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
