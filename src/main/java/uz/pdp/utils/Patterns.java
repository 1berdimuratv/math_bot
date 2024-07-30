package uz.pdp.utils;

public interface Patterns {
    /*String PHONE = "^\\+998\\d{9}$";
    String PHONE2 = "^\\998\\d{9}$";
    String PHONE2 = "^\\d{10,16}$";*/
    String NAME_FOR_RUSSIANS = "[А-Я][а-я]+\\s[А-Я][а-я]+";
    String NAME_FOR_ENG_OR_UZ = "^[A-Z][a-z]+(?:-[A-Z][a-z]+)?\\s+[A-Z][a-z]+";
}
