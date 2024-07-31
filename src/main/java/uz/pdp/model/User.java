package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.model.enams.Lang;
import uz.pdp.model.enams.UserState;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private Long chatId; // unique
    private String fio;
    private Lang lang;
    private UserState userState;
}
