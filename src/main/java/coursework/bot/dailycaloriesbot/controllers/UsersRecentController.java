package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.Recent;
import coursework.bot.dailycaloriesbot.entities.UsersRecent;
import coursework.bot.dailycaloriesbot.repositories.RecentRepository;
import coursework.bot.dailycaloriesbot.repositories.UsersRecentRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersRecentController {

    private final UsersRecentRepository usersRecentRepository;
    private final RecentRepository recentRepository;

    public UsersRecentController(UsersRecentRepository usersRecentRepository, RecentRepository recentRepository) {
        this.usersRecentRepository = usersRecentRepository;
        this.recentRepository = recentRepository;
    }

    public UsersRecent getUserRecentByTelegramId(long telegramId) {
        Optional<UsersRecent> userData = usersRecentRepository.findById(telegramId);
        return userData.orElse(null);
    }

    public void createUserRecent(UsersRecent usersRecent) {
        usersRecentRepository.save(new UsersRecent(usersRecent.getTelegramId()));
    }

    public void addRecent(long telegramId, String product) {
        Optional<UsersRecent> userData = usersRecentRepository.findById(telegramId);
        UsersRecent usersRecent;
        if (userData.isEmpty()) {
            usersRecent = new UsersRecent(telegramId);
            createUserRecent(usersRecent);
        } else {
            usersRecent = userData.get();
        }
        List<Recent> recentList = usersRecent.getRecentList();
        if (recentList.contains(new Recent(telegramId, product))) {
            deleteRecent(telegramId, product);
            recentList.add(new Recent(telegramId, product));
        } else if (recentList.size() >= 15) {
            deleteRecent(telegramId, recentList.get(0).getProduct());
            recentList.add(new Recent(telegramId, product));
        } else {
            recentList.add(new Recent(telegramId, product));
        }
        usersRecentRepository.save(usersRecent);
    }

    public void deleteRecent(long telegramId, String product) {
        Optional<UsersRecent> userData = usersRecentRepository.findById(telegramId);
        if (userData.isEmpty()) {
            return;
        }
        UsersRecent usersRecent = userData.get();
        List<Recent> recentList = usersRecent.getRecentList();
        recentList.remove(new Recent(telegramId, product));
        Optional<Recent> recent = recentRepository.findByTelegramIdAndProduct(telegramId, product);
        recent.ifPresent(value -> recentRepository.deleteById(value.getId()));
        usersRecentRepository.save(usersRecent);
    }
}