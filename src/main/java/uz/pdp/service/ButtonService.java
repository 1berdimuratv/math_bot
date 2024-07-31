package uz.pdp.service;



import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.utils.MessageKey.*;

public class ButtonService {
    private static I18nService i18n = I18nService.getInstance();
    public static ReplyKeyboardMarkup chooseLanguage() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton(i18n.getMsg(LANG_UZ));
        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(LANG_RU));
        row1.add(button1);
        row1.add(button2);
        return finalCase(List.of(row1));
    }

    public static ReplyKeyboardMarkup settings(String name,String number) {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton(i18n.getMsg(CHANGE_LANG));
        row1.add(button1);

        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(CHANGE_NAME).formatted(name));
        row2.add(button2);

        KeyboardButton button4 = new KeyboardButton(i18n.getMsg(BACK));
        row3.add(button4);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        return finalCase(rows);
    }

    public static ReplyKeyboard mainMenu() {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(i18n.getMsg(MENU));
        row2.add(i18n.getMsg(FEEDBACK));
        row2.add(i18n.getMsg(SETTINGS));
        keyboardRows.add(row1);
        keyboardRows.add(row2);

        return finalCase(keyboardRows);
    }

    public static ReplyKeyboard back() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(BACK));
        row.add(button2);
        rows.add(row);
        return finalCase(rows);
    }

    private static ReplyKeyboardMarkup finalCase(List<KeyboardRow> keyboardRows){
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(keyboardRows);
        replyMarkup.setIsPersistent(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        return replyMarkup;
    }
}
