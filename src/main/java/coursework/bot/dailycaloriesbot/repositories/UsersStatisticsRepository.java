package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.UsersStatistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersStatisticsRepository extends CrudRepository<UsersStatistics, Long> {
    @Query("SELECT us.telegramId FROM UsersStatistics us WHERE mod(us.daysInBot, :days) = 0")
    Optional<List<Long>> getAllTelegramIdByDaysInBot(int days);
}