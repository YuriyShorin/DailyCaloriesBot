package coursework.bot.dailycaloriesbot.view;

import coursework.bot.dailycaloriesbot.controllers.ProductsController;
import coursework.bot.dailycaloriesbot.controllers.UsersFavouritesController;
import coursework.bot.dailycaloriesbot.controllers.UsersRegistrationDataController;
import coursework.bot.dailycaloriesbot.controllers.UsersStatisticsController;
import coursework.bot.dailycaloriesbot.entities.UsersRegistrationData;
import coursework.bot.dailycaloriesbot.view.handlers.CallbackQueryHandler;
import coursework.bot.dailycaloriesbot.view.handlers.CommandsHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    private final UsersRegistrationDataController usersRegistrationDataController;
    private final UsersStatisticsController usersStatisticsController;
    private final ProductsController productsController;
    private final UsersFavouritesController usersFavouritesController;


    @Autowired
    public TelegramFacade(UsersRegistrationDataController usersRegistrationDataController, ProductsController productsController, UsersStatisticsController usersStatisticsController, UsersFavouritesController usersFavouritesController) {
        this.usersRegistrationDataController = usersRegistrationDataController;
        this.usersStatisticsController = usersStatisticsController;
        this.productsController = productsController;
        this.usersFavouritesController = usersFavouritesController;
    }

    public BotApiMethod<?> handleUpdate(Update update, DailyCaloriesBot bot) { // получен update от Telegram
        if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            return callbackQueryHandler.processCallBackQuery(update.getCallbackQuery(), usersRegistrationDataController, usersStatisticsController, usersFavouritesController, productsController, bot);
        }
        if (update.hasMessage() && update.getMessage().hasText()) { // если получено сообщение
            String messageText = update.getMessage().getText();
            if (messageText.equals("/start")) { // получена команда начать
                return processCommand(update, messageText);
            }
            UsersRegistrationData user = usersRegistrationDataController.getUserByTelegramId(update.getMessage().getFrom().getId());
            if (user == null) {
                return null;
            }
            String stageOfRegistration = user.getWasRegistered();
            return switch (stageOfRegistration) {
                case "yes", "no registration" -> processCommand(update, update.getMessage().getText());
                case "age" -> processCommand(update, "age");
                case "weight" -> processCommand(update, "weight");
                case "height" -> processCommand(update, "height");
                case "change_age" -> processCommand(update, "change_age");
                case "change_height" -> processCommand(update, "change_height");
                case "change_weight" -> processCommand(update, "change_weight");
                default -> null;
            };
        }
        return null;
    }

    private BotApiMethod<?> processCommand(Update update, String command) { // функция, обрабатывающая полученную команду
        CommandsHandler commandsHandler = new CommandsHandler(); // обработчик команд
        if (command.startsWith("/") && !command.equals("/start")) {
            return commandsHandler.unknownCommandReceived(update);
        }
        return switch (command) {
            case "/start" ->
                    commandsHandler.startCommandReceived(update, usersRegistrationDataController); // если получена команда /start
            case "age" -> commandsHandler.ageCommandReceived(update, usersRegistrationDataController);
            case "weight" -> commandsHandler.weightCommandReceived(update, usersRegistrationDataController);
            case "height" -> commandsHandler.heightCommandReceived(update, usersRegistrationDataController);
            case "change_age" -> commandsHandler.changeAgeCommandReceived(update, usersRegistrationDataController);
            case "change_height" ->
                    commandsHandler.changeHeightCommandReceived(update, usersRegistrationDataController);
            case "change_weight" ->
                    commandsHandler.changeWeightCommandReceived(update, usersRegistrationDataController);
            case "\uD83C\uDF54 Добавить продукт" -> commandsHandler.addProductCommandReceived(update);
            case "\uD83D\uDCA7 Добавить стакан" ->
                    commandsHandler.addGlassOfWaterCommandReceived(update, usersStatisticsController);
            case "\uD83D\uDCCA Статистика" -> commandsHandler.getStatisticsCommandReceived(update);
            case "⚙️ Изменить данные" ->
                    commandsHandler.changeDataCommandReceived(update, usersRegistrationDataController);
            case "❓Помощь" -> commandsHandler.getHelpCommandReceived(update);
            case "\uD83C\uDF71 Моя норма" ->
                    commandsHandler.getNormCommandReceived(update, usersRegistrationDataController);
            case "Выбрать другой продукт" -> commandsHandler.findAnotherProductCommandReceived(update);
            case "Далее ➡️" ->
                    commandsHandler.nextListOfProductsCommandReceived(update, usersRegistrationDataController);
            case "⬅️ Назад" ->
                    commandsHandler.previousListOfProductsCommandReceived(update, usersRegistrationDataController);
            default ->
                    commandsHandler.productReceived(update, usersRegistrationDataController, productsController, usersFavouritesController);
        };
    }
}