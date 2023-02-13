package coursework.bot.dailycaloriesbot.view.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardModel {
    public ReplyKeyboardMarkup getReplyKeyboardMarkup(List<String> buttonNames, int numberOfButtonsInRow) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < buttonNames.size(); ++i) {
            row.add(new KeyboardButton(buttonNames.get(i)));
            if ((i + 1) % numberOfButtonsInRow == 0) {
                keyboard.add(row);
                row = new KeyboardRow();
            }
        }
        keyboard.add(row);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
