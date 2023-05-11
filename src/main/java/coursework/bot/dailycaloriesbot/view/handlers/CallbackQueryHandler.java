package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.constants.Constants;
import coursework.bot.dailycaloriesbot.controllers.*;
import coursework.bot.dailycaloriesbot.entities.*;
import coursework.bot.dailycaloriesbot.view.DailyCaloriesBot;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import coursework.bot.dailycaloriesbot.view.keyboards.ReplyKeyboardModel;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class CallbackQueryHandler {

    public BotApiMethod<?> processCallBackQuery(CallbackQuery buttonQuery, UsersRegistrationDataController usersController, UsersStatisticsController usersStatisticsController, UsersFavouritesController usersFavouritesController, UsersRecentController usersRecentController, ProductsController productsController, DailyCaloriesBot bot) {
        String data = buttonQuery.getData();
        if (data.startsWith("GENDER")) {
            return getGenderAnswer(buttonQuery, data.substring(6, 13), usersController);
        } else if (data.startsWith("REGISTRATION")) {
            return getRegistrationAnswer(buttonQuery, data.substring(12), usersController, bot);
        } else if (data.startsWith("GOAL")) {
            return getGoalAnswer(buttonQuery, data.substring(4), usersController);
        } else if (data.startsWith("ACTIVITY")) {
            return getActivityAnswer(buttonQuery, data.substring(8), usersController);
        } else if (data.startsWith("IS_REGISTRATION_CORRECT")) {
            return checkIsRegistrationCorrect(buttonQuery, data.substring(23), bot);
        } else if (data.startsWith("CHANGE")) {
            return changeData(buttonQuery, data, usersController);
        } else if (data.startsWith("change_GENDER")) {
            return changeGender(buttonQuery, data.substring(13, 20), usersController);
        } else if (data.startsWith("change_GOAL")) {
            return changeGoal(buttonQuery, data.substring(11), usersController);
        } else if (data.startsWith("change_ACTIVITY")) {
            return changeActivity(buttonQuery, data.substring(15), usersController);
        } else if (data.startsWith("WAS_REGISTRATION_CONTINUED")) {
            return continueRegistration(buttonQuery, data.substring(26), usersController);
        } else if (data.startsWith("ADD_PRODUCT")) {
            return addProduct(buttonQuery, data.substring(11), usersFavouritesController, usersRecentController, bot);
        } else if (data.startsWith("PRODUCT_INFO")) {
            return productInfo(buttonQuery, data.substring(12), usersController, usersStatisticsController, usersFavouritesController, usersRecentController, productsController, bot);
        } else if (data.startsWith("STATS")) {
            return statistics(buttonQuery, data.substring(5), usersStatisticsController, usersController);
        } else if (data.startsWith("DELETE")) {
            return deleteAccount(buttonQuery, data.substring(6), usersController, bot);
        } else if (data.startsWith("WEIGHT_TRACKING")) {
            return weightTracking(buttonQuery, data.substring(15), usersController, bot);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> weightTracking(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController, DailyCaloriesBot bot) {
        EditMessageText editMessageText;
        if (answer.equals(Constants.CHANGE)) {
            usersController.updateWasRegistered(buttonQuery.getFrom().getId(), "weight_tracking");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_WEIGHT_QUESTION,
                    new InlineKeyboardMarkup(), false);
        } else {
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Данные сохранены!", new InlineKeyboardMarkup(), false);
            sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, "Вам доступен основной функционал бота", false));
        }
        return editMessageText;
    }

    private BotApiMethod<?> getRegistrationAnswer(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController, DailyCaloriesBot bot) {
        EditMessageText editMessageText;
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        if (answer.equals(Constants.YES)) {
            usersController.updateWasRegistered(buttonQuery.getFrom().getId(), "gender");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GENDER_QUESTION,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GENDER_BUTTONS, "GENDER"), false);
        } else {
            usersController.updateWasRegistered(buttonQuery.getFrom().getId(), "no registration");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Вы всегда можете дополнить свои данные в разделе \"Изменить данные\"",
                    new InlineKeyboardMarkup(), false);
            sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, "Вам доступен основной функционал бота", false));
        }
        return editMessageText;
    }

    private BotApiMethod<?> getGenderAnswer(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateWasRegistered(userId, "age");
        usersController.updateGender(userId, answer);
        return createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_AGE_QUESTION, new InlineKeyboardMarkup(), true);
    }

    private BotApiMethod<?> getGoalAnswer(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateWasRegistered(userId, "activity");
        usersController.updateGoal(userId, answer);
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        return createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_ACTIVITY_QUESTION,
                inlineKeyboardModel.createInlineKeyboardMarkup(Constants.ACTIVITY_BUTTONS, "ACTIVITY"), true);
    }

    private BotApiMethod<?> getActivityAnswer(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateWasRegistered(userId, "yes");
        usersController.updateActivity(userId, answer);
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> checkIsRegistrationCorrect(CallbackQuery buttonQuery, String answer, DailyCaloriesBot bot) {
        EditMessageText editMessageText;
        if (answer.equals(Constants.CHANGE)) {
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Что вы хотите изменить?",
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.CHANGE_BUTTONS, "CHANGE"), false);
        } else {
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Данные сохранены!", new InlineKeyboardMarkup(), false);
            sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, "Вам доступен основной функционал бота", false));
        }
        return editMessageText;
    }

    private BotApiMethod<?> changeData(CallbackQuery buttonQuery, String data, UsersRegistrationDataController usersController) {
        EditMessageText editMessageText;
        long userId = buttonQuery.getFrom().getId();
        if (data.endsWith("Пол")) {
            usersController.updateWasRegistered(userId, "change_gender");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GENDER_QUESTION,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GENDER_BUTTONS, "change_GENDER"), false);
        } else if (data.endsWith("Возраст")) {
            usersController.updateWasRegistered(userId, "change_age");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_AGE_QUESTION,
                    new InlineKeyboardMarkup(), false);
        } else if (data.endsWith("Вес")) {
            usersController.updateWasRegistered(userId, "change_weight");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_WEIGHT_QUESTION,
                    new InlineKeyboardMarkup(), false);
        } else if (data.endsWith("Рост")) {
            usersController.updateWasRegistered(userId, "change_height");
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_HEIGHT_QUESTION,
                    new InlineKeyboardMarkup(), false);
        } else if (data.endsWith("Цель")) {
            usersController.updateWasRegistered(userId, "change_goal");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GOAL_QUESTION,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GOAL_BUTTONS, "change_GOAL"), false);
        } else if (data.endsWith("Активность")) {
            usersController.updateWasRegistered(userId, "change_activity");
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_ACTIVITY_QUESTION,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.ACTIVITY_BUTTONS, "change_ACTIVITY"), true);

        } else {
            usersController.deleteUser(buttonQuery.getFrom().getId());
            usersController.createUser(new UsersRegistrationData(buttonQuery.getFrom().getId(), "gender"));
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GENDER_QUESTION,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GENDER_BUTTONS, "GENDER"), false);
        }
        return editMessageText;
    }

    private BotApiMethod<?> changeGender(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateGender(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> changeGoal(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateGoal(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> changeActivity(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        long userId = buttonQuery.getFrom().getId();
        usersController.updateActivity(userId, answer);
        usersController.updateWasRegistered(userId, "yes");
        return createFinalRegistrationMessage(buttonQuery, usersController);
    }

    private BotApiMethod<?> continueRegistration(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController) {
        EditMessageText editMessageText;
        String stageOfRegistration = usersController.getUserByTelegramId(buttonQuery.getFrom().getId())
                .getWasRegistered();
        if (answer.equals(Constants.YES)) {
            switch (stageOfRegistration) {
                case "gender" -> {
                    InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GENDER_QUESTION,
                            inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GENDER_BUTTONS, "change_GENDER"), false);
                }
                case "age" ->
                        editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_AGE_QUESTION,
                                new InlineKeyboardMarkup(), false);
                case "weight" ->
                        editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_WEIGHT_QUESTION,
                                new InlineKeyboardMarkup(), false);
                case "height" ->
                        editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_HEIGHT_QUESTION,
                                new InlineKeyboardMarkup(), false);
                case "goal" -> {
                    InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_GOAL_QUESTION,
                            inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GOAL_BUTTONS, "change_GOAL"), false);
                }
                default -> {
                    InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.WHAT_IS_YOUR_ACTIVITY_QUESTION,
                            inlineKeyboardModel.createInlineKeyboardMarkup(Constants.ACTIVITY_BUTTONS, "change_ACTIVITY"), true);
                }
            }
        } else {
            long userId = buttonQuery.getFrom().getId();
            usersController.deleteUser(userId);
            usersController.createUser(new UsersRegistrationData(userId, "no"));
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.HelloMessage,
                    inlineKeyboardModel.createInlineKeyboardMarkup(Constants.YES_OR_NO_BUTTONS, "REGISTRATION"), true);
        }
        return editMessageText;
    }

    private BotApiMethod<?> addProduct(CallbackQuery buttonQuery, String answer, UsersFavouritesController usersFavouritesController, UsersRecentController usersRecentController, DailyCaloriesBot bot) {
        EditMessageText editMessageText;
        if (answer.equals(Constants.FAVOURITES)) {
            UsersFavourites usersFavourites = usersFavouritesController.getUserFavouritesByTelegramId(buttonQuery.getFrom()
                    .getId());
            if (!usersFavourites.getFavourites().isEmpty()) {
                List<Favourites> favorites = new ArrayList<>(usersFavourites.getFavourites());
                List<String> products = new ArrayList<>();
                for (Favourites favourites : favorites) {
                    products.add(favourites.getProduct());
                }
                products.add("Выбрать другой продукт");
                ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(buttonQuery.getMessage().getChatId());
                sendMessage.setText("Выберите продукт");
                sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(products, 1, true));
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Вы выбрали избранные продукты.", new InlineKeyboardMarkup(), false);
                sendMessage(bot, sendMessage);
            } else {
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "У вас нет избранных продуктов.", new InlineKeyboardMarkup(), false);
            }
        } else if (answer.equals(Constants.RECENT)) {
            UsersRecent usersRecent = usersRecentController.getUserRecentByTelegramId(buttonQuery.getFrom().getId());
            if (!usersRecent.getRecentList().isEmpty()) {
                List<Recent> recentList = new ArrayList<>(usersRecent.getRecentList());
                List<String> products = new ArrayList<>();
                for (Recent recent : recentList) {
                    products.add(recent.getProduct());
                }
                products.add("Выбрать другой продукт");
                ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(buttonQuery.getMessage().getChatId());
                sendMessage.setText("Выберите продукт");
                sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(products, 1, true));
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Вы выбрали недавно используемые продукты.", new InlineKeyboardMarkup(), false);
                sendMessage(bot, sendMessage);
            } else {
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery,
                        "Вы еще не выбрали ни одного продукта.\nПора это сделать.",
                        new InlineKeyboardMarkup(), false);
            }
        } else {
            editMessageText = (EditMessageText) createEditMessageText(buttonQuery,
                    "Вы перенаправлены в главное меню.", new InlineKeyboardMarkup(), false);
        }
        return editMessageText;
    }

    private BotApiMethod<?> productInfo(CallbackQuery buttonQuery, String metadata, UsersRegistrationDataController usersController, UsersStatisticsController usersStatisticsController, UsersFavouritesController usersFavouritesController, UsersRecentController usersRecentController, ProductsController productsController, DailyCaloriesBot bot) {
        EditMessageText editMessageText;
        String[] metadataArray = metadata.split("/");
        int productId = Integer.parseInt(metadataArray[0]);
        int grams = Integer.parseInt(metadataArray[1]);
        String answer = metadataArray[2];
        Products product = productsController.getProductById(productId);
        switch (answer) {
            case Constants.ADD -> {
                if (grams == 100) {
                    usersRecentController.addRecent(buttonQuery.getFrom().getId(), product.getProduct());
                } else {
                    usersRecentController.addRecent(buttonQuery.getFrom().getId(), product.getProduct() + ", " + grams);
                }
                usersStatisticsController.increaseIntake(buttonQuery.getFrom().getId(), product, grams);
                usersController.removeUsersPreviousPage(buttonQuery.getFrom().getId());
                usersController.removeUsersNextPage(buttonQuery.getFrom().getId());
                usersController.removeUsersLastProduct(buttonQuery.getFrom().getId());
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.getUserDailyIntakeMessage(usersStatisticsController.getUserByTelegramId(buttonQuery.getFrom()
                                .getId())),
                        new InlineKeyboardMarkup(), true);
                sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, "Продукт был добавлен", false));
            }
            case Constants.MORE -> {
                List<Products> listOfProducts;
                if (usersController.getUsersLastProduct(buttonQuery.getFrom().getId()) == null) {
                    listOfProducts = productsController.getProductsByName("%" + product.getProduct()
                            .toLowerCase() + "%");
                    usersController.putUsersLastProduct(buttonQuery.getFrom().getId(), product.getProduct());
                } else {
                    listOfProducts = productsController.getProductsByName("%" + usersController.getUsersLastProduct(buttonQuery.getFrom()
                                    .getId())
                            .toLowerCase() + "%");
                }
                if (listOfProducts.isEmpty()) {
                    return new SendMessage(buttonQuery.getMessage().getChatId().toString(), "Товары не найдены");
                } else if (listOfProducts.size() >= 100) {
                    sendMessage(bot, CommandsHandler.fulfilUserPages(listOfProducts, usersController, buttonQuery.getFrom()
                            .getId(), buttonQuery.getMessage().getChatId(), grams));
                } else {
                    List<String> listOfNamesOfProducts = new ArrayList<>();
                    for (Products listOfProduct : listOfProducts) {
                        String name = listOfProduct.getProduct();
                        listOfNamesOfProducts.add(name);
                    }
                    listOfNamesOfProducts.add("Выбрать другой продукт");
                    ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите продукт");
                    sendMessage.setChatId(buttonQuery.getMessage().getChatId());
                    sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(listOfNamesOfProducts, 1, true));
                    sendMessage(bot, sendMessage);
                }
                if (usersController.getUsersLastProduct(buttonQuery.getFrom().getId()) == null) {
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Будут показаны продукты, содержащие: <b>" + product.getProduct() + ".</b>",
                            new InlineKeyboardMarkup(), true);
                } else {
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Будут показаны продукты, содержащие: <b>" + usersController.getUsersLastProduct(buttonQuery.getFrom()
                                    .getId()) + ".</b>",
                            new InlineKeyboardMarkup(), true);
                }
            }
            case Constants.ADD_TO_FAVOURITES -> {
                usersController.removeUsersPreviousPage(buttonQuery.getFrom().getId());
                usersController.removeUsersNextPage(buttonQuery.getFrom().getId());
                usersController.removeUsersLastProduct(buttonQuery.getFrom().getId());
                if (grams == 100) {
                    if (usersFavouritesController.addFavourite(buttonQuery.getFrom().getId(), product.getProduct())) {
                        editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Продукт был добавлен в избранное",
                                new InlineKeyboardMarkup(), false);
                    } else {
                        editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Продукт не был добавлен в избранное",
                                new InlineKeyboardMarkup(), false);
                    }
                } else if (usersFavouritesController.addFavourite(buttonQuery.getFrom()
                        .getId(), product.getProduct() + ", " + grams)) {
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Продукт был добавлен в избранное",
                            new InlineKeyboardMarkup(), false);
                } else {
                    editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Продукт не был добавлен в избранное",
                            new InlineKeyboardMarkup(), false);
                }
                sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, Constants.getNumberOfProductsInFavouritesMessage(usersFavouritesController.getUserFavouritesByTelegramId(buttonQuery.getFrom()
                        .getId())), true));
            }
            case Constants.DELETE_FROM_FAVOURITES -> {
                usersController.removeUsersPreviousPage(buttonQuery.getFrom().getId());
                usersController.removeUsersNextPage(buttonQuery.getFrom().getId());
                usersController.removeUsersLastProduct(buttonQuery.getFrom().getId());
                usersFavouritesController.deleteFavourite(buttonQuery.getFrom().getId(), product.getProduct());
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Продукт был удален из избранного",
                        new InlineKeyboardMarkup(), true);
                sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, Constants.getNumberOfProductsInFavouritesMessage(usersFavouritesController.getUserFavouritesByTelegramId(buttonQuery.getFrom()
                        .getId())), true));
            }
            case Constants.CHANGE_GRAMS -> {
                SendMessage sendMessage = new SendMessage(buttonQuery.getMessage().getChatId()
                        .toString(), product.getProduct());
                ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
                if (product.getProduct().length() > 49) {
                    forceReplyKeyboard.setInputFieldPlaceholder("Яблоко, (число грамм)");
                } else {
                    forceReplyKeyboard.setInputFieldPlaceholder(product.getProduct() + ", (число грамм)");

                }
                sendMessage.setReplyMarkup(forceReplyKeyboard);
                sendMessage(bot, sendMessage);
                sendMessage(bot, new SendMessage(buttonQuery.getMessage().getChatId()
                        .toString(), "Вставьте продукт с нужным количеством грамм.\nНапример:\n" + product.getProduct() + ", (число грамм)"));
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, "Вы хотите изменить граммовку данного продукта:", new InlineKeyboardMarkup(), false);
            }
            default -> {
                usersController.removeUsersPreviousPage(buttonQuery.getFrom().getId());
                usersController.removeUsersNextPage(buttonQuery.getFrom().getId());
                usersController.removeUsersLastProduct(buttonQuery.getFrom().getId());
                editMessageText = (EditMessageText) createEditMessageText(buttonQuery, Constants.getProductAddedMessage(product, grams / 100.0),
                        new InlineKeyboardMarkup(), true);
                sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, Constants.getProductWontBeCountedMessage(usersStatisticsController.getUserByTelegramId(buttonQuery.getFrom()
                        .getId())), true));
            }
        }
        return editMessageText;
    }

    private BotApiMethod<?> statistics(CallbackQuery buttonQuery, String answer, UsersStatisticsController usersController, UsersRegistrationDataController usersRegistrationDataController) {
        UsersStatistics user = usersController.getUserByTelegramId(buttonQuery.getFrom().getId());
        return switch (answer) {
            case "День" ->
                    createEditMessageText(buttonQuery, Constants.getUserDailyIntakeMessage(user), new InlineKeyboardMarkup(), true);
            case "Неделя" ->
                    createEditMessageText(buttonQuery, Constants.getUserWeeklyIntakeMessage(user), new InlineKeyboardMarkup(), true);
            case "Месяц" ->
                    createEditMessageText(buttonQuery, Constants.getUserMonthlyIntakeMessage(user), new InlineKeyboardMarkup(), true);
            case "За все время" ->
                    createEditMessageText(buttonQuery, Constants.getUserAllTimeIntakeMessage(user), new InlineKeyboardMarkup(), true);
            case "Отслеживание веса" -> {
                double currentWeight = usersRegistrationDataController.getUserByTelegramId(buttonQuery.getFrom().getId()).getWeight();
                yield createEditMessageText(buttonQuery, Constants.getUserWeightChange(user, currentWeight), new InlineKeyboardMarkup(), true);
            }
            default ->
                    createEditMessageText(buttonQuery, Constants.getUserAllStatsMessage(user), new InlineKeyboardMarkup(), true);
        };
    }

    private BotApiMethod<?> createFinalRegistrationMessage(CallbackQuery buttonQuery, UsersRegistrationDataController usersController) {
        UsersRegistrationData user = usersController.getUserByTelegramId(buttonQuery.getFrom().getId());
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        return createEditMessageText(buttonQuery, Constants.getIsAllRightMessage(user),
                inlineKeyboardModel.createInlineKeyboardMarkup(Constants.YES_OR_CHANGE_BUTTONS, "IS_REGISTRATION_CORRECT"), true);
    }

    private BotApiMethod<?> deleteAccount(CallbackQuery buttonQuery, String answer, UsersRegistrationDataController usersController, DailyCaloriesBot bot) {
        if (answer.equals(Constants.YES)) {
            usersController.deleteUser(buttonQuery.getFrom().getId());
            return createEditMessageText(buttonQuery, "Аккаунт успешно удален", new InlineKeyboardMarkup(), false);
        } else {
            sendMessage(bot, (SendMessage) createFinalKeyboard(buttonQuery, "Спасибо, что остаетесь с нами!", false));
            return createEditMessageText(buttonQuery, "Аккаунт не будет удален", new InlineKeyboardMarkup(), false);
        }
    }

    private BotApiMethod<?> createEditMessageText(CallbackQuery buttonQuery, String text, InlineKeyboardMarkup inlineKeyboardMarkup, boolean isParseMode) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(buttonQuery.getMessage().getChatId().toString());
        editMessageText.setMessageId(buttonQuery.getMessage().getMessageId());
        if (isParseMode) {
            editMessageText.setParseMode(ParseMode.HTML);
        }
        if (!text.equals("")) {
            editMessageText.setText(text);
        }
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    private BotApiMethod<?> createFinalKeyboard(CallbackQuery buttonQuery, String text, boolean isParseMode) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(buttonQuery.getMessage().getChatId());
        if (isParseMode) {
            sendMessage.setParseMode(ParseMode.HTML);
        }
        sendMessage.setText(text);
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardModel.getReplyKeyboardMarkup(Constants.FINAL_KEYBOARD, 2, false);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private void sendMessage(DailyCaloriesBot bot, SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}