package coursework.bot.dailycaloriesbot.schedule;

import coursework.bot.dailycaloriesbot.controllers.UsersStatisticsController;
import coursework.bot.dailycaloriesbot.repositories.UsersStatisticsRepository;
import coursework.bot.dailycaloriesbot.view.DailyCaloriesBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        Optional<List<Long>> usersData = usersStatisticsRepository.getAllTelegramIdByDaysInBot(1);
        if (usersData.isPresent()) {
            List<Long> listOfTelegramId = usersData.get();
            for (Long id : listOfTelegramId) {
                SendMessage sendMessage = usersController.weightTracking(id);
                try {
                    bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException("SCHEDULE");
                }
            }
        }
    }
}

