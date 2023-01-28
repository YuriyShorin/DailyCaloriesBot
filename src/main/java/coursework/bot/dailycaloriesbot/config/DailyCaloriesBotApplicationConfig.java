package coursework.bot.dailycaloriesbot.config;

import coursework.bot.dailycaloriesbot.DailyCaloriesBot;
import coursework.bot.dailycaloriesbot.TelegramFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class DailyCaloriesBotApplicationConfig {
    private final DailyCaloriesBotConfig config;

    public DailyCaloriesBotApplicationConfig(DailyCaloriesBotConfig config) {
        this.config = config;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(config.getWebhookPath()).build();
    }

    @Bean
    public DailyCaloriesBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        DailyCaloriesBot bot = new DailyCaloriesBot(telegramFacade, setWebhook);
        bot.setBotToken(config.getBotToken());
        bot.setBotUsername(config.getBotName());
        bot.setBotPath(config.getWebhookPath());
        return bot;
    }
}
