package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.UsersRecent;
import org.springframework.data.repository.CrudRepository;

public interface UsersRecentRepository extends CrudRepository<UsersRecent, Long> {
}