package coursework.bot.dailycaloriesbot.view;

import coursework.bot.dailycaloriesbot.controllers.ProductsController;
import coursework.bot.dailycaloriesbot.controllers.UsersController;
import coursework.bot.dailycaloriesbot.entities.Users;
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

    final UsersController usersController;
    final ProductsController productsController;

    @Autowired
    public TelegramFacade(UsersController usersController, ProductsController productsController) {
        this.usersController = usersController;
        this.productsController = productsController;
    }

    public BotApiMethod<?> handleUpdate(Update update, DailyCaloriesBot bot) { // получен update от Telegram
        if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            return callbackQueryHandler.processCallBackQuery(update.getCallbackQuery(), usersController, productsController, bot);
        }
        if (update.hasMessage() && update.getMessage().hasText()) { // если получено сообщение
            String messageText = update.getMessage().getText();
            if (messageText.equals("/start")) { // получена команда начать
                return processCommand(update, messageText);
            }
            Users user = usersController.getUserByTelegramId(update.getMessage().getFrom().getId());
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
                    commandsHandler.startCommandReceived(update, usersController); // если получена команда /start
            case "age" -> commandsHandler.ageCommandReceived(update, usersController);
            case "weight" -> commandsHandler.weightCommandReceived(update, usersController);
            case "height" -> commandsHandler.heightCommandReceived(update, usersController);
            case "change_age" -> commandsHandler.changeAgeCommandReceived(update, usersController);
            case "change_height" -> commandsHandler.changeHeightCommandReceived(update, usersController);
            case "change_weight" -> commandsHandler.changeWeightCommandReceived(update, usersController);
            case "\uD83C\uDF54 Добавить продукт" -> commandsHandler.addProductCommandReceived(update);
            case "\uD83D\uDCA7 Добавить стакан" ->
                    commandsHandler.addGlassOfWaterCommandReceived(update, usersController);
            case "\uD83D\uDCCA Статистика" -> commandsHandler.getStatisticsCommandReceived(update, usersController);
            case "⚙️ Изменить данные" -> commandsHandler.changeDataCommandReceived(update, usersController);
            case "❓Помощь" -> commandsHandler.getHelpCommandReceived(update, usersController);
            case "\uD83C\uDF71 Моя норма" -> commandsHandler.getNormCommandReceived(update, usersController);
            case "Выбрать другой продукт" -> commandsHandler.findAnotherProductCommandReceived(update);
            case "Далее ---->" -> commandsHandler.nextListOfProductsCommandReceived(update, usersController);
            case "<---- Назад" -> commandsHandler.previousListOfProductsCommandReceived(update,usersController);
            default -> commandsHandler.productReceived(update,usersController, productsController);
        };
    }
}