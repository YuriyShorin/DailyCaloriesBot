package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.controller.UsersController;
import coursework.bot.dailycaloriesbot.entity.Users;
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
            return getGenderAnswer(buttonQuery, data.substring(6, 13), usersController);
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
            return changeGoal(buttonQuery, data.substring(11), usersController);
        } else if (data.startsWith("WAS_REGISTRATION_CONTINUED")) {
            return continueRegistration(buttonQuery, data.substring(26), usersController);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> getGenderAnswer(CallbackQuery buttonQuery, String answer, UsersController usersController) {
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
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        if (answer.equals("Да ✅")) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId())
                    .getId(), "gender");
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"}), "GENDER"));
            editMessageText.setText("Какого вы пола?");
        } else {
            usersController.updateWasRegistered(usersController.getUserByTelegramId(buttonQuery.getFrom().getId())
                    .getId(), "no registration");
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
            editMessageText.setText("Вы всегда можете дополнить свои данные в разделе \"Изменить данные\"");
        }
        return editMessageText;
    }

    private BotApiMethod<?> getGoalAnswer(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateWasRegistered(userId, "yes");
        usersController.updateGoal(userId, answer);
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> checkIsRegistrationCorrect(CallbackQuery buttonQuery, String answer) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        if (answer.equals("Да ✅")) {
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
            editMessageText.setText("Ваши данные сохранены." +
                    "\nНажмите /continue для продолжения");
        } else {
            editMessageText.setText("Что вы хотите изменить?");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Пол", "Возраст", "Вес", "Рост", "Цель", "Пройти регистрацию заново"}), "CHANGE"));
        }
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
        } else if (data.endsWith("Пройти регистрацию заново")) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            usersController.deleteUser(buttonQuery.getFrom().getId());
            usersController.createUser(new Users(buttonQuery.getFrom().getId(), "gender"));
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"}), "GENDER"));
            editMessageText.setText("Какого вы пола?");
        } else {
            usersController.updateWasRegistered(userId, "change_goal");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText.setText("Какова ваша цель похудения?");
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"}), "change_GOAL"));
        }
        return editMessageText;
    }

    private BotApiMethod<?> changeGender(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateGender(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> changeGoal(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        long userId = usersController.getUserByTelegramId(buttonQuery.getFrom().getId()).getId();
        usersController.updateGoal(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> continueRegistration(CallbackQuery buttonQuery, String answer, UsersController usersController) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        long userId = buttonQuery.getFrom().getId();
        String stageOfRegistration = usersController.getUserByTelegramId(userId).getWasRegistered();
        if (answer.equals("Да ✅")) {
            switch (stageOfRegistration) {
                case "gender" -> {
                    editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Мужчина \uD83D\uDC71\u200D♂️", "Женщина \uD83D\uDC71\u200D♀️"}), "GENDER"));
                    editMessageText.setText("Какого вы пола?");
                }
                case "age" -> {
                    editMessageText.setText("Сколько вам лет?");
                    editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
                }
                case "weight" -> {
                    editMessageText.setText("Какой у вас вес?");
                    editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
                }
                case "height" -> {
                    editMessageText.setText("Какой у вас рост?");
                    editMessageText.setReplyMarkup(new InlineKeyboardMarkup());
                }
                case "goal" -> {
                    editMessageText.setText("Какова ваша цель похудения?");
                    editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Похудение", "Поддержание веса", "Набор массы"}), "GOAL"));
                }
            }
        } else {
            usersController.deleteUser(userId);
            usersController.createUser(new Users(userId, "no"));
            editMessageText.setText("Желаете ли зарегистрироваться?");
            editMessageText.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of(new String[]{"Да ✅", "Нет ❌"}), "REGISTRATION")); // добавление двух кнопок
        }
        return editMessageText;
    }

    private BotApiMethod<?> createFinalRegistrationMessage(CallbackQuery buttonQuery, UsersController usersController) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        editMessageText.setText("Ваши данные изменены." +
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
}