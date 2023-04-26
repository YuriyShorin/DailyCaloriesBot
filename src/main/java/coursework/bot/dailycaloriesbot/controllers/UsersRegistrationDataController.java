package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.*;
import coursework.bot.dailycaloriesbot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsersRegistrationDataController {

    private final UsersRegistrationDataRepository usersRegistrationDataRepository;
    private final UsersStatisticsRepository usersStatisticsRepository;

    private final UsersFavouritesRepository usersFavouritesRepository;

    private final UsersRecentRepository usersRecentRepository;
    private final Map<Long, List<String>> usersPreviousPage = new HashMap<>();
    private final Map<Long, List<String>> usersNextPage = new HashMap<>();
    private final Map<Long, String> usersLastProduct = new HashMap<>();

    @Autowired
    public UsersRegistrationDataController(UsersRegistrationDataRepository usersRegistrationDataRepository, UsersStatisticsRepository usersStatisticsRepository, UsersFavouritesRepository usersFavouritesRepository, UsersRecentRepository usersRecentRepository) {
        this.usersRegistrationDataRepository = usersRegistrationDataRepository;
        this.usersStatisticsRepository = usersStatisticsRepository;
        this.usersFavouritesRepository = usersFavouritesRepository;
        this.usersRecentRepository = usersRecentRepository;
    }

    public UsersRegistrationData getUserByTelegramId(long telegramId) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(telegramId);
        return usersData.orElse(null);
    }

    public void createUser(UsersRegistrationData user) {
        usersRegistrationDataRepository.save(new UsersRegistrationData(user.getTelegramId(), user.getWasRegistered()));
        usersStatisticsRepository.save(new UsersStatistics(user.getTelegramId()));
        usersFavouritesRepository.save(new UsersFavourites(user.getTelegramId()));
        usersRecentRepository.save(new UsersRecent(user.getTelegramId()));
    }

    public void updateWasRegistered(long id, String wasRegistered) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setWasRegistered(wasRegistered);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateGender(long id, String gender) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setGender(gender);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateAge(long id, int age) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setAge(age);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateWeight(long id, double weight) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setWeight(weight);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateHeight(long id, double height) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setHeight(height);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateGoal(long id, String goal) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setGoal(goal);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void updateActivity(long id, String activity) {
        Optional<UsersRegistrationData> usersData = usersRegistrationDataRepository.findById(id);
        if (usersData.isPresent()) {
            UsersRegistrationData user = usersData.get();
            user.setActivity(activity);
            usersRegistrationDataRepository.save(user);
        }
    }

    public void deleteUser(long telegramId) {
        usersStatisticsRepository.deleteById(telegramId);
        usersRegistrationDataRepository.deleteById(telegramId);
    }

    public Map<Long, List<String>> getUsersPreviousPage() {
        return usersPreviousPage;
    }

    public void putUsersPreviousPage(Long id, List<String> page) {
        this.usersPreviousPage.put(id, page);
    }

    public Map<Long, List<String>> getUsersNextPage() {
        return usersNextPage;
    }

    public void putUsersNextPage(Long id, List<String> page) {
        this.usersNextPage.put(id, page);
    }

    public void removeUsersNextPage(Long id) {
        usersNextPage.remove(id);
    }

    public void removeUsersPreviousPage(Long id) {
        usersPreviousPage.remove(id);
    }

    public String getUsersLastProduct(long userId) {
        return usersLastProduct.get(userId);
    }

    public void putUsersLastProduct(Long userId, String product) {
        usersLastProduct.put(userId, product);
    }

    public void removeUsersLastProduct(Long userId) {
        usersLastProduct.remove(userId);
    }
}