package uz.pdp.service;



import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.model.Category;
import uz.pdp.model.OrderProduct;
import uz.pdp.model.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import uz.pdp.model.User;
import uz.pdp.model.enams.Lang;
import uz.pdp.utils.GlobalVar;

import static uz.pdp.utils.MessageKey.*;

public class ButtonService {

    private static I18nService i18n = I18nService.getInstance();
    private static ProductService productService = ProductService.getInstance();
    public static ReplyKeyboardMarkup myOrders(List<OrderProduct> products){
        User user = GlobalVar.getUSER();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow temp = new KeyboardRow();
        for (OrderProduct product : products) {
            Product byId = productService.findById(product.getProductId(),user.getLang()).get();
            temp.add(new KeyboardButton("❌ "+byId.getName()));
            rows.add(temp);
            temp = new KeyboardRow();
        }
        /*KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton(i18n.getMsg(SEND_ORDER));
        row1.add(button1);
        rows.add(row1);*/
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(CLEAR_BASKET));
        KeyboardButton button3 = new KeyboardButton(i18n.getMsg(BACK));
        row2.add(button2);
        row2.add(button3);
        rows.add(row2);
        return finalCase(rows);
    }

    public static ReplyKeyboardMarkup chooseProduct(Product product) {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton("1");
        KeyboardButton button2 = new KeyboardButton("2");
        KeyboardButton button3 = new KeyboardButton("3");
        row1.add(button1);
        row1.add(button2);
        row1.add(button3);

        KeyboardButton button4 = new KeyboardButton("4");
        KeyboardButton button5 = new KeyboardButton("5");
        KeyboardButton button6 = new KeyboardButton("6");
        row2.add(button4);
        row2.add(button5);
        row2.add(button6);

        KeyboardButton button7 = new KeyboardButton("7");
        KeyboardButton button8 = new KeyboardButton("8");
        KeyboardButton button9 = new KeyboardButton("9");
        row3.add(button7);
        row3.add(button8);
        row3.add(button9);


        KeyboardButton back = new KeyboardButton(i18n.getMsg(BACK));

        row4.add(back);

        return finalCase(List.of(row1,row2,row3,row4));
    }

    public static ReplyKeyboardMarkup chooseLanguage() {

        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton(i18n.getMsg(LANG_UZ));
        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(LANG_RU));
        row1.add(button1);
        row1.add(button2);

        return finalCase(List.of(row1));
    }

    /*public static ReplyKeyboardMarkup sharePhoneNumber() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton(i18n.getMsg(PHONE_BUTTON));
        button1.setRequestContact(true);
        row1.add(button1);
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(List.of(row1));
        replyMarkup.setIsPersistent(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        return replyMarkup;
    }
*/
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
    public static ReplyKeyboard categoriesMenu(List<Category> categories, boolean basket) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        if (basket) {
            KeyboardRow order = new KeyboardRow();
            order.add(i18n.getMsg(TAKE_ORDER));
            order.add(i18n.getMsg(IN_BASKET));
            keyboardRows.add(order);
        }
        for (int i = 0; i < categories.size(); i = i + 2) {
            KeyboardRow row = new KeyboardRow(2);
            row.add(categories.get(i).getName());
            if (i + 1 < categories.size())
                row.add(categories.get(i + 1).getName());
            keyboardRows.add(row);
        }
        KeyboardRow back = new KeyboardRow();
        back.add(i18n.getMsg(BACK));
        keyboardRows.add(back);
        return finalCase(keyboardRows);
    }
    public static ReplyKeyboard productsMenu(List<Product> categories) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (int i = 0; i < categories.size(); i = i + 2) {
            KeyboardRow row = new KeyboardRow(2);
            row.add(categories.get(i).getName());
            if (i + 1 < categories.size())
                row.add(categories.get(i + 1).getName());

            keyboardRows.add(row);
        }
        KeyboardRow back = new KeyboardRow();
        back.add(i18n.getMsg(BACK));
        keyboardRows.add(back);
        return finalCase(keyboardRows);
    }

    private static ReplyKeyboardMarkup finalCase(List<KeyboardRow> keyboardRows){
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(keyboardRows);
        replyMarkup.setIsPersistent(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        return replyMarkup;
    }

    public static ReplyKeyboard back() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button2 = new KeyboardButton(i18n.getMsg(BACK));
        row.add(button2);
        rows.add(row);
        return finalCase(rows);
    }
}
