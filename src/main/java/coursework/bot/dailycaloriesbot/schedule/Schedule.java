package coursework.bot.dailycaloriesbot.schedule;

import coursework.bot.dailycaloriesbot.controllers.UsersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Schedule {

    private final UsersController usersController;

    @Autowired
    public Schedule(UsersController usersController) {
        this.usersController = usersController;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void ResetDailyFieldsInDatabase() {
        usersController.zeroGlassesOfWaterAndDailyIntakeForAllUsers();
    }
}

