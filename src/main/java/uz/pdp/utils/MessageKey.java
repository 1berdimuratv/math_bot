package uz.pdp.utils;

import lombok.Getter;

@Getter
public enum MessageKey {
    LANG_UZ( "lang.uz", "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha"),
    LANG_RU("lang.ru","\uD83C\uDDF7\uD83C\uDDFA Русский"),
    STARTED_MSG("started.msg", "started"),
    USERS_FIO("name.request", "Ismingiz va familiyangizni kiriting (Abdullayev Ismoil)"),
    CHOOSE_LANG("choose.lang", "Tilni tanlang"),
    BACK("back", "Ortga"),
    BASKET("basket", "Savat"),
    ADD_TO_BASKET("add.basket", "Savatga qoshish"),
    IN_BASKET("in.basket", "Savatda:"),
    ALL_PRODUCTS("all.products", "Mahsulotlar :"),
    NAME_ERROR("name.error","Ismingizda xato"),
    ALL("all", "Jami : "),
    SUCCESSFUL("success", "Tayyor"),
    MAIN_MENU("main.menu", "Quyidagilarrdan birini tanlang"),
    ORDER_MENU("order.menu", "Nimadan boshlaymiz?"),
    CHANGE_LANG("change.lang", "Tilni ozgartirish"),
    CHANGE_NAME("change.name", "Ismni ozgartirish"),
    CHOOSE_PROD("choose.product", "Ismni ozgartirish"),
    SETTINGS("settings", "Sozlamalar"),
    FEEDBACK("feedback", "Fikr bildirish"),
    HELPER("helper", "Maxsulot miqdorini tanlang yoki yozib kiriting:"),
    ASK_OPINION("ask.feedback", "Fikringizni yuboing"),
    TAKE_OPINION("take.feedback", "Fikr-mulohazangiz uchun rahmat"),
    ORDERS("orders", "Mening buyurtmalarim"),
    ORDER_ACCEPTED("order.accepted", "✅"),
    CLEAR_BASKET("clear.basket", "Savatni tozalash"),
    TAKE_ORDER("take.order", "\uD83D\uDE97 Buyurtma qilish"),
    EMPTY_ORDERS("empty.orders", "Siz hech narsa buyurtma bermagansiz"),
    MENU("menu", "Menyu");

    private String key; // unique
    private String val;

    MessageKey(String key, String val) {
        this.key = key;
        this.val = val;
    }
}
