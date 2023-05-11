package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.Favourites;
import coursework.bot.dailycaloriesbot.entities.UsersFavourites;
import coursework.bot.dailycaloriesbot.repositories.FavouritesRepository;
import coursework.bot.dailycaloriesbot.repositories.UsersFavouritesRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class UsersFavouritesController {

    private final UsersFavouritesRepository usersFavouritesRepository;
    private final FavouritesRepository favouritesRepository;

    public UsersFavouritesController(UsersFavouritesRepository usersFavouritesRepository, FavouritesRepository favouritesRepository) {
        this.usersFavouritesRepository = usersFavouritesRepository;
        this.favouritesRepository = favouritesRepository;
    }

    public UsersFavourites getUserFavouritesByTelegramId(long telegramId) {
        Optional<UsersFavourites> usersData = usersFavouritesRepository.findById(telegramId);
        return usersData.orElse(null);
    }

    public void createUserFavourites(UsersFavourites usersFavourites) {
        usersFavouritesRepository.save(new UsersFavourites(usersFavourites.getTelegramId()));
    }

    public Set<Favourites> getFavourites(long telegramId) {
        Optional<UsersFavourites> usersData = usersFavouritesRepository.findById(telegramId);
        return usersData.map(UsersFavourites::getFavourites).orElse(null);
    }

    public boolean addFavourite(long telegramId, String product) {
        Optional<UsersFavourites> usersData = usersFavouritesRepository.findById(telegramId);
        UsersFavourites usersFavourites;
        if (usersData.isEmpty()) {
            usersFavourites = new UsersFavourites(telegramId);
            createUserFavourites(usersFavourites);
        } else {
            usersFavourites = usersData.get();
        }
        Set<Favourites> favourites = usersFavourites.getFavourites();
        favourites.add(new Favourites(telegramId, product));
        usersFavouritesRepository.save(usersFavourites);
        return true;
    }

    public void deleteFavourite(long telegramId, String product) {
        Optional<UsersFavourites> usersData = usersFavouritesRepository.findById(telegramId);
        if (usersData.isEmpty()) {
            return;
        }
        UsersFavourites usersFavourites = usersData.get();
        Set<Favourites> favourites = usersFavourites.getFavourites();
        favourites.remove(new Favourites(telegramId, product));
        Optional<Favourites> favourite = favouritesRepository.findByTelegramIdAndProduct(telegramId, product);
        favourite.ifPresent(value -> favouritesRepository.deleteById(value.getId()));
        usersFavouritesRepository.save(usersFavourites);
    }
}