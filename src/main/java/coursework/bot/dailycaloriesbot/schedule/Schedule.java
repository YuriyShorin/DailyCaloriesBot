package coursework.bot.dailycaloriesbot.schedule;

import coursework.bot.dailycaloriesbot.controllers.UsersStatisticsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Schedule {

    private final UsersStatisticsController usersController;

    @Autowired
    public Schedule(UsersStatisticsController usersController) {
        this.usersController = usersController;
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
}

