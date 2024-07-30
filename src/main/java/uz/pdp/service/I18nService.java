package uz.pdp.service;

import lombok.Getter;
import lombok.val;
import uz.pdp.model.enams.Lang;
import uz.pdp.utils.GlobalVar;
import uz.pdp.utils.MessageKey;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nService {
    @Getter
    private static final I18nService instance = new I18nService();
    private I18nService() {}

    public String getMsg(MessageKey key, Lang lang) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(
                    "message",
                    Locale.forLanguageTag(lang.name())
            );
            return resourceBundle.getString(key.getKey());
        }catch (Throwable e) {
        }
        return key.getVal();
    }

    public String getMsg(MessageKey key) {
        try {

            ResourceBundle resourceBundle = ResourceBundle.getBundle(
                    "message",
                    Locale.forLanguageTag(GlobalVar.getUSER().getLang().name())
            );
            return resourceBundle.getString(key.getKey());
        }catch (Throwable e) {
        }
        return key.getVal();
    }

}
