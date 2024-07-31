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
    NAME_ERROR("name.error","Ismingizda xato"),
    SUCCESSFUL("success", "Tayyor"),
    MAIN_MENU("main.menu", "Quyidagilarrdan birini tanlang"),
    ORDER_MENU("take.question", "Nimadan boshlaymiz?"),
    CHANGE_LANG("change.lang", "Tilni ozgartirish"),
    CHANGE_NAME("change.name", "Ismni ozgartirish"),
    SETTINGS("settings", "Sozlamalar"),
    FEEDBACK("feedback", "Fikr bildirish"),
    ASK_OPINION("ask.feedback", "Fikringizni yuboing"),
    TAKE_OPINION("take.feedback", "Fikr-mulohazangiz uchun rahmat"),
    ORDER_ACCEPTED("accepted", "✅"),
    MENU("question", "Menyu");

    private String key; // unique
    private String val;

    MessageKey(String key, String val) {
        this.key = key;
        this.val = val;
    }
}
