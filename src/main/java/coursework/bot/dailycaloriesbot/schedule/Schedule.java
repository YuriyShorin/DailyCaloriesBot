package coursework.bot.dailycaloriesbot.schedule;

import coursework.bot.dailycaloriesbot.constants.Constants;
import coursework.bot.dailycaloriesbot.controllers.UsersStatisticsController;
import coursework.bot.dailycaloriesbot.entities.UsersRegistrationData;
import coursework.bot.dailycaloriesbot.repositories.UsersStatisticsRepository;
import coursework.bot.dailycaloriesbot.view.DailyCaloriesBot;
import coursework.bot.dailycaloriesbot.view.keyboards.InlineKeyboardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@EnableScheduling
@Component
public class Schedule {

    private final UsersStatisticsController usersController;

    private final UsersStatisticsRepository usersStatisticsRepository;

    private final DailyCaloriesBot bot;


    @Autowired
    public Schedule(UsersStatisticsController usersController, UsersStatisticsRepository usersStatisticsRepository, DailyCaloriesBot bot) {
        this.usersController = usersController;
        this.usersStatisticsRepository = usersStatisticsRepository;
        this.bot = bot;
    }

    @Scheduled(cron = "@daily")
    private void everyDayChange() {
        usersController.zeroDailyGlassesOfWaterAndDailyIntakeForAllUsers();
        usersController.incrementDaysInBotForAllUsers();
    }

    @Scheduled(cron = "@weekly")
    private void everyWeekChange() {
        usersController.zeroWeeklyGlassesOfWaterAndWeeklyIntakeForAllUsers();
    }

    @Scheduled(cron = "@monthly")
    private void everyMonthChange() {
        usersController.zeroMonthlyGlassesOfWaterAndMonthlyIntakeForAllUsers();
    }

    @Scheduled(cron = "0 0 12 * * *")
    private void weightTracking() {
        Optional<List<Long>> usersData = usersStatisticsRepository.getAllTelegramIdByDaysInBot(7);
        if (usersData.isPresent()) {
            List<Long> listOfTelegramId = usersData.get();
            for (Long id : listOfTelegramId) {
                SendMessage sendMessage = new SendMessage();
                InlineKeyboardModel inlineKeyboardModel = new InlineKeyboardModel(new InlineKeyboardMarkup());
                sendMessage.setReplyMarkup(inlineKeyboardModel.createInlineKeyboardMarkup(Constants.NO_OR_CHANGE_BUTTONS_WEIGHT_TRACKING, "WEIGHT_TRACKING"));
                UsersRegistrationData user = usersController.getUserRegistrationDataByTelegramId(id);
                sendMessage.setText("Пришло время повторно измерить свой вес.\n\n" + Constants.getWeightDataMessage(user));
                sendMessage.setChatId(id);
                sendMessage.setParseMode(ParseMode.HTML);
                try {
                    bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException("SCHEDULE");
                }
            }
        }
    }
}