package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.Users;
import coursework.bot.dailycaloriesbot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    public Users getUserByTelegramId(long telegramId) {
        Optional<Users> usersData = usersRepository.findById(telegramId);
        return usersData.orElse(null);
    }

    public void createUser(Users user) {
        usersRepository.save(new Users(user.getTelegramId(), user.getWasRegistered(), user.getFindProduct()));
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

    public void updateFindProduct(long id, String isFindProduct) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setFindProduct(isFindProduct);
            usersRepository.save(user);
        }
    }

    public void zeroGlassesOfWaterAndDailyCalorieIntakeForAllUsers() {
        Iterable<Users> usersData = usersRepository.findAll();
        while (usersData.iterator().hasNext()) {
            Users user = usersData.iterator().next();
            user.setGlassesOfWater(0);
            user.setDailyCalorieIntake(0);
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

    public void increaseDailyCalorieIntake(long id, double numberToIncrease) {
        Optional<Users> usersData = usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users user = usersData.get();
            user.setDailyCalorieIntake(user.getDailyCalorieIntake() + numberToIncrease);
            usersRepository.save(user);
        }
    }

    public void deleteUser(long telegramId) {
        usersRepository.deleteById(telegramId);
    }
}
