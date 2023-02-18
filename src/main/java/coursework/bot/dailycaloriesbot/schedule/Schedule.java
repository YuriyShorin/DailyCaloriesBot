package coursework.bot.dailycaloriesbot.schedule;

import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Schedule {
    @Autowired
    UsersRepository usersRepository;

    @Scheduled(cron = "0 0 0 * * *")
    private void zeroOutGlassesOfWater() {
        Iterable<Users> usersData = usersRepository.findAll();
        while (usersData.iterator().hasNext()) {
            Users user = usersData.iterator().next();
            user.setGlassesOfWater(0);
            usersRepository.save(user);
        }
    }
}
