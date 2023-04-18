package coursework.bot.dailycaloriesbot.view.handlers;

import coursework.bot.dailycaloriesbot.constants.Constants;
import coursework.bot.dailycaloriesbot.controllers.ProductsController;
import coursework.bot.dailycaloriesbot.controllers.UsersController;
import coursework.bot.dailycaloriesbot.entities.Products;
import coursework.bot.dailycaloriesbot.entities.Users;
import coursework.bot.dailycaloriesbot.utills.NumbersUtil;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import coursework.bot.dailycaloriesbot.view.keyboards.ReplyKeyboardModel;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandsHandler {

    public BotApiMethod<?> startCommandReceived(Update update, UsersController usersController) { // обработчик команды /start
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage;
        if (user == null) {
            sendMessage = createRegistrationYesOrNoMessage(Constants.HelloMessage, "REGISTRATION", update.getMessage()
                    .getChatId().toString());
            usersController.createUser(new Users(update.getMessage().getFrom().getId(), "no"));
        } else if (user.getWasRegistered().equals("no")) {
            sendMessage = createRegistrationYesOrNoMessage(Constants.HelloMessage, "REGISTRATION", update.getMessage()
                    .getChatId().toString());
        } else if (user.getWasRegistered().equals("yes") || user.getWasRegistered().equals("no registration")) {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "С возвращением!");
            ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
            ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardModel.getReplyKeyboardMarkup(Constants.FINAL_KEYBOARD, 2, false);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        } else {
            sendMessage = createRegistrationYesOrNoMessage("Хотели бы продолжить регистрацию?", "WAS_REGISTRATION_CONTINUED",
                    update.getMessage().getChatId().toString());
        }
        return sendMessage;
    }

    public BotApiMethod<?> ageCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Integer age = NumbersUtil.parseInt(messageText);
        if (age == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        if (!NumbersUtil.checkAge(age)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный возраст: " + age);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateAge(userId, age);
        usersController.updateWasRegistered(userId, "weight");
        return new SendMessage(update.getMessage().getChatId().toString(), Constants.WHAT_IS_YOUR_WEIGHT_QUESTION);
    }

    public BotApiMethod<?> weightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double weight = NumbersUtil.parseDouble(messageText);
        if (weight == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Вес должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkWeight(weight)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный вес: " + weight);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateWeight(userId, weight);
        usersController.updateWasRegistered(userId, "height");
        return new SendMessage(update.getMessage().getChatId().toString(), Constants.WHAT_IS_YOUR_HEIGHT_QUESTION);
    }

    public BotApiMethod<?> heightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double height = NumbersUtil.parseDouble(messageText);
        if (height == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Рост должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkHeight(height)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный рост: " + height);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateHeight(userId, height);
        usersController.updateWasRegistered(userId, "goal");
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId()
                .toString(), Constants.WHAT_IS_YOUR_GOAL_QUESTION);
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.GOAL_BUTTONS, "GOAL"));
        return sendMessage;
    }

    public BotApiMethod<?> changeAgeCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Integer age = NumbersUtil.parseInt(messageText);
        if (age == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Возраст должен быть целым числом");
        }
        if (!NumbersUtil.checkAge(age)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный возраст: " + age);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateAge(userId, age);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> changeWeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double weight = NumbersUtil.parseDouble(messageText);
        if (weight == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Вес должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkWeight(weight)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный вес: " + weight);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateWeight(userId, weight);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> changeHeightCommandReceived(Update update, UsersController usersController) {
        String messageText = update.getMessage().getText();
        Double height = NumbersUtil.parseDouble(messageText);
        if (height == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Рост должен быть целым или дробным числом");
        }
        if (!NumbersUtil.checkHeight(height)) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Вы ввели некорректный рост: " + height);
        }
        long userId = update.getMessage().getFrom().getId();
        usersController.updateHeight(userId, height);
        return createFinalRegistrationMessage(userId, usersController);
    }

    public BotApiMethod<?> addProductCommandReceived(Update update) {
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Выберите продукт");
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(List.of("продукты", "Другой"), "ADD_PRODUCT"));
        return sendMessage;
    }

    public BotApiMethod<?> addGlassOfWaterCommandReceived(Update update, UsersController usersController) {
        long userId = update.getMessage().getFrom().getId();
        usersController.incrementGlassesOfWater(userId);
        return new SendMessage(String.valueOf(userId), "За день было выпито: " + usersController.getUserByTelegramId(userId)
                .getGlassesOfWater());
    }

    public BotApiMethod<?> getStatisticsCommandReceived(Update update, UsersController usersController) {
        return new SendMessage(update.getMessage().getChatId().toString(), "getStatisticsCommandReceived");
    }

    public BotApiMethod<?> changeDataCommandReceived(Update update, UsersController usersController) {
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(Constants.getAllDataMessage(user));
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.SAVE_OR_CHANGE_BUTTONS, "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }

    public BotApiMethod<?> getHelpCommandReceived(Update update, UsersController usersController) {
        return new SendMessage(update.getMessage().getChatId().toString(), "getHelpCommandReceived");
    }

    public BotApiMethod<?> getNormCommandReceived(Update update, UsersController usersController) {
        Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
        if (user.getGender() == null || user.getAge() == 0 || user.getWeight() == 0 ||
                user.getHeight() == 0 || user.getGoal() == null || user.getActivity() == null) {
            return new SendMessage(update.getMessage().getChatId()
                    .toString(), "Необходимо заполнить все данные для подсчета нормы");
        }
        double calories;
        if (user.getGender().equals("Мужчина")) {
            calories = 9.99 * user.getWeight() + 6.25 * user.getHeight() - 4.92 * user.getAge() + 5;
        } else {
            calories = 9.99 * user.getWeight() + 6.25 * user.getHeight() - 4.92 * user.getAge() - 161;
        }

        switch (user.getActivity()) {
            case "Минимальная" -> calories *= 1.2;
            case "Слабая" -> calories *= 1.375;
            case "Умеренная" -> calories *= 1.55;
            case "Тяжелая" -> calories *= 1.7;
            case "Экстремальная" -> calories *= 1.9;
            default -> calories *= 1;
        }
        if (user.getGoal().equals("Похудение")) {
            calories *= 0.8;
        }
        if (user.getGoal().equals("Набор массы")) {
            calories *= 1.2;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(Constants.getFormulaResultMessage(calories));
        return sendMessage;
    }

    public BotApiMethod<?> productReceived(Update update, UsersController usersController, ProductsController productsController) {
        if (update.getMessage().getText().length() < 3) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Введите не менее 3-х символов");
        }
        String productInfo = update.getMessage().getText();
        String[] productInfoSplit;
        String productName;
        int grams;
        if (productInfo.contains(",")) {
            if (productInfo.contains(", ")) {
                productInfoSplit = productInfo.split(", ");
            } else {
                productInfoSplit = productInfo.split(",");
            }
            productName = productInfoSplit[0];
            try {
                grams = Integer.parseInt(productInfoSplit[1]);
            } catch (NumberFormatException exception) {
                return new SendMessage(update.getMessage().getChatId().toString(), "Введите целое число после запятой");
            }
        } else {
            productName = productInfo;
            grams = 100;
        }
        Products product = productsController.getProductByName(productName);
        if (product != null) {
            if (usersController.getUsersLastProduct(update.getMessage().getFrom().getId()) != null) {
                if (!usersController.getUsersLastProduct(update.getMessage().getFrom().getId()).equals(productName)
                        && !productName.toLowerCase()
                        .contains(usersController.getUsersLastProduct(update.getMessage().getFrom().getId())
                                .toLowerCase())) {
                    usersController.putUsersLastProduct(update.getMessage().getFrom().getId(), productName);
                }
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setParseMode(ParseMode.HTML);
            sendMessage.setText(Constants.getProductAddedMessage(product, grams / 100.0));
            InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
            sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.ADD_OR_MORE_OR_INFO_BUTTONS, "PRODUCT_INFO" + product.getId() + "/" + grams + "/"));
            return sendMessage;
        }
        usersController.putUsersLastProduct(update.getMessage().getFrom().getId(), productName);
        productName = productName.toLowerCase();
        List<Products> listOfProducts = productsController.getProductsByName("%" + productName + "%");
        if (listOfProducts.isEmpty()) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Товар не найден");
        } else if (listOfProducts.size() >= 100) {
            int middle = listOfProducts.size() / 2;
            List<String> listOfNamesOfProductsFirstPart = new ArrayList<>();
            List<String> listOfNamesOfProductsSecondPart = new ArrayList<>();

            listOfNamesOfProductsFirstPart.add("Далее ---->");
            for (int i = 0; i < middle; i++) {
                String name = listOfProducts.get(i).getProduct();
                listOfNamesOfProductsFirstPart.add(name + ", " + grams);
            }
            listOfNamesOfProductsFirstPart.add("Выбрать другой продукт");
            listOfNamesOfProductsFirstPart.add("Далее ---->");

            usersController.putUsersPreviousPage(update.getMessage().getFrom().getId(), listOfNamesOfProductsFirstPart);

            listOfNamesOfProductsSecondPart.add("<---- Назад");
            for (int i = middle; i < listOfProducts.size(); i++) {
                String name = listOfProducts.get(i).getProduct();
                listOfNamesOfProductsSecondPart.add(name + ", " + grams);
            }
            listOfNamesOfProductsSecondPart.add("Выбрать другой продукт");
            listOfNamesOfProductsSecondPart.add("<---- Назад");
            usersController.putUsersNextPage(update.getMessage().getFrom().getId(), listOfNamesOfProductsSecondPart);

            ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Выберите продукт");
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(listOfNamesOfProductsFirstPart, 1, true));
            return sendMessage;
        }

        List<String> listOfNamesOfProducts = new ArrayList<>();
        for (Products listOfProduct : listOfProducts) {
            String name = listOfProduct.getProduct();
            listOfNamesOfProducts.add(name + ", " + grams);
        }
        listOfNamesOfProducts.add("Выбрать другой продукт");
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите продукт");
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(listOfNamesOfProducts, 1, true));
        return sendMessage;
    }

    public BotApiMethod<?> unknownCommandReceived(Update update) { // обработчик неизвестной команды
        if (update.getMessage().getText().startsWith("/")) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Получена неизвестная команда");
        }
        return null;
    }

    private BotApiMethod<?> createFinalRegistrationMessage(long userId, UsersController usersController) {
        usersController.updateWasRegistered(userId, "yes");
        Users user = usersController.getUserByTelegramId(userId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(userId));
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(Constants.getIsAllRightMessage(user));
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.YES_OR_CHANGE_BUTTONS, "IS_REGISTRATION_CORRECT"));
        return sendMessage;
    }


    private SendMessage createRegistrationYesOrNoMessage(String messageText, String callbackData, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(messageText);
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.YES_OR_NO_BUTTONS, callbackData));
        return sendMessage;
    }

    public BotApiMethod<?> findAnotherProductCommandReceived(Update update) {
        return new SendMessage(update.getMessage().getFrom().getId().toString(), "Введите продукт");
    }

    public BotApiMethod<?> nextListOfProductsCommandReceived(Update update, UsersController usersController) {
        Map<Long, List<String>> page = usersController.getUsersNextPage();
        List<String> listOfNamesOfProducts = page.get(update.getMessage().getFrom().getId());
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите продукт");
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(listOfNamesOfProducts, 1, true));
        return sendMessage;
    }

    public BotApiMethod<?> previousListOfProductsCommandReceived(Update update, UsersController usersController) {
        Map<Long, List<String>> page = usersController.getUsersPreviousPage();
        List<String> listOfNamesOfProducts = page.get(update.getMessage().getFrom().getId());
        ReplyKeyboardModel replyKeyboardModel = new ReplyKeyboardModel();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите продукт");
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyKeyboardModel.getReplyKeyboardMarkup(listOfNamesOfProducts, 1, true));
        return sendMessage;
    }

}