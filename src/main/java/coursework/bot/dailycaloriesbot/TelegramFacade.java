package coursework.bot.dailycaloriesbot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()){
            return new SendMessage(update.getMessage().getChatId().toString(), "Саляй пидор");
        }
        return null;
    }
}
