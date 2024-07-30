package uz.pdp.model.enams;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    USER_STARTED(1),
    CHOOSE_LANGUAGE(2),
    USERS_FIO(3),
    MAIN_MENU(4),
    CHANG_NUM(5),
    CHANG_NAM(6),
    MENU(7),
    ORDERS(8),
    FEEDBACK(9),
    SETTINGS(10),
    ORDERING(11),
    CHOOSING_AMOUNT(12),
    WRITE_LOCATION(13),
    SHARE_LOCATION(14);

    private final int step;

    public static UserState getByStep(int step) {
        return values()[step-1];
    }
}
