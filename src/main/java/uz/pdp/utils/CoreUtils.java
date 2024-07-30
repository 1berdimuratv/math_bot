package uz.pdp.utils;

import uz.pdp.model.Order;
import uz.pdp.model.enams.Lang;

public interface CoreUtils {

    /*static boolean checkPhoneNumber(String phone) {
        return phone.matches(Patterns.PHONE) || phone.matches(Patterns.PHONE2);
    }*/
    static boolean checkNameFormat(String name, Lang lang) {
        if (lang == Lang.RU) return name.matches(Patterns.NAME_FOR_RUSSIANS);
        else return name.matches(Patterns.NAME_FOR_ENG_OR_UZ);
    }
    static String[] splitAddress(String body) {
        body = body.substring(body.indexOf("$")+1);
        String[] address = new String[4];
        for (int i = 0; i < 3; i++) {
            address[i] = body.substring(0,body.indexOf("$"));
            body = body.substring(body.indexOf("$")+1);
        }
        address[3] = body;
        return address;
    }


}
