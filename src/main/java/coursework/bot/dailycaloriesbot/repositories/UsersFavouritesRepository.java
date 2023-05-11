package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.UsersFavourites;

import org.springframework.data.repository.CrudRepository;

public interface UsersFavouritesRepository extends CrudRepository<UsersFavourites, Long> {
}