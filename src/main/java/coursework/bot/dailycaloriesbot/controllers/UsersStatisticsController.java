package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.constants.Constants;
import coursework.bot.dailycaloriesbot.entities.Products;
import coursework.bot.dailycaloriesbot.entities.UsersRegistrationData;
import coursework.bot.dailycaloriesbot.entities.UsersStatistics;
import coursework.bot.dailycaloriesbot.repositories.UsersStatisticsRepository;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersStatisticsController {

    private final UsersStatisticsRepository usersStatisticsRepository;

    private final UsersRegistrationDataController userController;


    @Autowired
    public UsersStatisticsController(UsersStatisticsRepository usersStatisticsRepository, UsersRegistrationDataController userController) {
        this.usersStatisticsRepository = usersStatisticsRepository;
        this.userController = userController;
    }

    public UsersStatistics getUserByTelegramId(long telegramId) {
        Optional<UsersStatistics> usersData = usersStatisticsRepository.findById(telegramId);
        return usersData.orElse(null);
    }

    public void zeroDailyGlassesOfWaterAndDailyIntakeForAllUsers() {
        Iterable<UsersStatistics> usersStatistics = usersStatisticsRepository.findAll();
        while (usersStatistics.iterator().hasNext()) {
            UsersStatistics user = usersStatistics.iterator().next();
            user.setDailyGlassesOfWater(0);
            user.setDailyCalorieIntake(0);
            user.setDailyProteinsIntake(0);
            user.setDailyFatsIntake(0);
            user.setDailyCarbohydratesIntake(0);
            usersStatisticsRepository.save(user);
        }
    }

    public void incrementGlassesOfWater(long id) {
        Optional<UsersStatistics> usersData = usersStatisticsRepository.findById(id);
        if (usersData.isPresent()) {
            UsersStatistics user = usersData.get();
            user.setDailyGlassesOfWater(user.getDailyGlassesOfWater() + 1);
            user.setWeeklyGlassesOfWater(user.getWeeklyGlassesOfWater() + 1);
            user.setMonthlyGlassesOfWater(user.getMonthlyGlassesOfWater() + 1);
            user.setAllTimeGlassesOfWater(user.getAllTimeGlassesOfWater() + 1);
            usersStatisticsRepository.save(user);
        }
    }

    public void increaseIntake(long id, Products product, int grams) {
        Optional<UsersStatistics> usersData = usersStatisticsRepository.findById(id);
        if (usersData.isPresent()) {
            UsersStatistics user = usersData.get();
            double coefficient = grams / 100.0;
            double calories = product.getKilocalories() * coefficient;
            double proteins = product.getProteins() * coefficient;
            double fats = product.getFats() * coefficient;
            double carbohydrates = product.getCarbohydrates() * coefficient;
            user.setDailyCalorieIntake(user.getDailyCalorieIntake() + calories);
            user.setDailyProteinsIntake(user.getDailyProteinsIntake() + proteins);
            user.setDailyFatsIntake(user.getDailyFatsIntake() + fats);
            user.setDailyCarbohydratesIntake(user.getDailyCarbohydratesIntake() + carbohydrates);
            user.setWeeklyCalorieIntake(user.getWeeklyCalorieIntake() + calories);
            user.setWeeklyProteinsIntake(user.getWeeklyProteinsIntake() + proteins);
            user.setWeeklyFatsIntake(user.getWeeklyFatsIntake() + fats);
            user.setWeeklyCarbohydratesIntake(user.getWeeklyCarbohydratesIntake() + carbohydrates);
            user.setMonthlyCalorieIntake(user.getMonthlyCalorieIntake() + calories);
            user.setMonthlyProteinsIntake(user.getMonthlyProteinsIntake() + proteins);
            user.setMonthlyFatsIntake(user.getMonthlyFatsIntake() + fats);
            user.setMonthlyCarbohydratesIntake(user.getMonthlyCarbohydratesIntake() + carbohydrates);
            user.setAllTimeCalorieIntake(user.getAllTimeCalorieIntake() + calories);
            user.setAllTimeProteinsIntake(user.getAllTimeProteinsIntake() + proteins);
            user.setAllTimeFatsIntake(user.getAllTimeFatsIntake() + fats);
            user.setAllTimeCarbohydratesIntake(user.getAllTimeCarbohydratesIntake() + carbohydrates);
            usersStatisticsRepository.save(user);
        }
    }

    public void incrementDaysInBotForAllUsers() {
        Iterable<UsersStatistics> usersData = usersStatisticsRepository.findAll();
        while (usersData.iterator().hasNext()) {
            UsersStatistics user = usersData.iterator().next();
            user.setDaysInBot(user.getDaysInBot() + 1);
            usersStatisticsRepository.save(user);
        }
    }

    public void zeroWeeklyGlassesOfWaterAndWeeklyIntakeForAllUsers() {
        Iterable<UsersStatistics> usersData = usersStatisticsRepository.findAll();
        while (usersData.iterator().hasNext()) {
            UsersStatistics user = usersData.iterator().next();
            user.setWeeklyGlassesOfWater(0);
            user.setWeeklyCalorieIntake(0);
            user.setWeeklyProteinsIntake(0);
            user.setWeeklyFatsIntake(0);
            user.setWeeklyCarbohydratesIntake(0);
            usersStatisticsRepository.save(user);
        }
    }

    public void zeroMonthlyGlassesOfWaterAndMonthlyIntakeForAllUsers() {
        Iterable<UsersStatistics> usersData = usersStatisticsRepository.findAll();
        while (usersData.iterator().hasNext()) {
            UsersStatistics user = usersData.iterator().next();
            user.setMonthlyGlassesOfWater(0);
            user.setMonthlyCalorieIntake(0);
            user.setMonthlyProteinsIntake(0);
            user.setMonthlyFatsIntake(0);
            user.setMonthlyCarbohydratesIntake(0);
            usersStatisticsRepository.save(user);
        }
    }

    public SendMessage weightTracking(Long id) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
        sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.NO_OR_CHANGE_BUTTONS_WEIGHT_TRACKING, "WEIGHT_TRACKING"));
        UsersRegistrationData user = userController.getUserByTelegramId(id);
        sendMessage.setText("Пришло время повторно измерить свой вес.\n\n" + Constants.getWeightDataMessage(user));
        sendMessage.setChatId(id);
        sendMessage.setParseMode(ParseMode.HTML);
        return sendMessage;
    }
}