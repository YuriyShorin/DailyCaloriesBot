package coursework.bot.dailycaloriesbot.controller;

import coursework.bot.dailycaloriesbot.entity.Users;
import coursework.bot.dailycaloriesbot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    public Users getUserByTelegramId(long telegramId) {
        return usersRepository.findByTelegramId(telegramId);
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

    public void deleteUser(long telegramId) {
        usersRepository.deleteById(usersRepository.findByTelegramId(telegramId).getId());
    }
}
