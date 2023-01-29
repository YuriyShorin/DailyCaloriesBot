package coursework.bot.dailycaloriesbot.controller;

import coursework.bot.dailycaloriesbot.model.DailyCaloriesBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class DailyCaloriesBotController {

    private final DailyCaloriesBot bot;

    public DailyCaloriesBotController(DailyCaloriesBot bot) {
        this.bot = bot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }
}
