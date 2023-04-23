package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.Favourites;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FavouritesRepository extends CrudRepository<Favourites, Long> {

    Optional<Favourites> findByProduct(String product);
}
