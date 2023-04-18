package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.Products;
import coursework.bot.dailycaloriesbot.entities.Users;
import coursework.bot.dailycaloriesbot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsersController {

    private final UsersRepository usersRepository;

    private final Map<Long, List<String>> usersPreviousPage = new HashMap<>();
    private final Map<Long, List<String>> usersNextPage = new HashMap<>();

    private final Map<Long, String> usersLastProduct = new HashMap<>();

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users getUserByTelegramId(long telegramId) {
        Optional<Users> usersData = usersRepository.findById(telegramId);
        return usersData.orElse(null);
    }

    public void createUser(Users user) {
        usersRepository.save(new Users(user.getTelegramId(), user.getWasRegistered()));
    }

    public void updateWasRegistered(long id, String wasRegistered) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setWasRegistered(wasRegistered);
            usersRepository.save(user);
        }
    }

    public void updateGender(long id, String gender) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setGender(gender);
            usersRepository.save(user);
        }
    }

    public void updateAge(long id, int age) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setAge(age);
            usersRepository.save(user);
        }
    }

    public void updateWeight(long id, double weight) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setWeight(weight);
            usersRepository.save(user);
        }
    }

    public void updateHeight(long id, double height) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setHeight(height);
            usersRepository.save(user);
        }
    }

    public void updateGoal(long id, String goal) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setGoal(goal);
            usersRepository.save(user);
        }
    }

    public void updateActivity(long id, String activity) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setActivity(activity);
            usersRepository.save(user);
        }
    }

    public void zeroGlassesOfWaterAndDailyIntakeForAllUsers() {
        Iterable<Users> usersData = usersRepository.findAll();
        while (usersData.iterator().hasNext()) {
            Users user = usersData.iterator().next();
            user.setGlassesOfWater(0);
            user.setDailyCalorieIntake(0);
            user.setDailyProteinsIntake(0);
            user.setDailyFatsIntake(0);
            user.setDailyCarbohydratesIntake(0);
            usersRepository.save(user);
        }
    }

    public void incrementGlassesOfWater(long id) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setGlassesOfWater(user.getGlassesOfWater() + 1);
            usersRepository.save(user);
        }
    }

    public void increaseDailyIntake(long id, Products products, int grams) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            double coefficient = grams / 100.0;
            user.setDailyCalorieIntake(user.getDailyCalorieIntake() + products.getKilocalories() * coefficient);
            user.setDailyProteinsIntake(user.getDailyProteinsIntake() + products.getProteins() * coefficient);
            user.setDailyFatsIntake(user.getDailyFatsIntake() + products.getFats() * coefficient);
            user.setDailyCarbohydratesIntake(user.getDailyCarbohydratesIntake() + products.getCarbohydrates() * coefficient);
            usersRepository.save(user);
        }
    }

    public void deleteUser(long telegramId) {
        usersRepository.deleteById(telegramId);
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
