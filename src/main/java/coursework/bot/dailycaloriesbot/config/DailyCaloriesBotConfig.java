package coursework.bot.dailycaloriesbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyCaloriesBotConfig {
    @Value("${bot.webhookPath}")
    String webhookPath;
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
}