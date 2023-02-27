package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> { }
