package coursework.bot.dailycaloriesbot.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyCaloriesBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;
    TelegramFacade telegramFacade;

    public DailyCaloriesBot(TelegramFacade telegramFacade, SetWebhook setWebhook) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update, this);
    }
}