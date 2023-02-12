package coursework.bot.dailycaloriesbot.view.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardModel {

    private final InlineKeyboardMarkup inlineKeyboardMarkup;

    public InlineKeyboardModel(InlineKeyboardMarkup inlineKeyboardMarkup) {
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createInlineKeyboardMarkup(List<String> buttonNames, String callBackData) { // метод для создания inline кнопок
        List<List<InlineKeyboardButton>> keyboardRow = new ArrayList<>();
        for (String name : buttonNames) {
            keyboardRow.add(createButton(name, callBackData + name));
        }
        inlineKeyboardMarkup.setKeyboard(keyboardRow);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
