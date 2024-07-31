package uz.pdp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Question {
    private final Integer id;
    private static int sequence = 1;
    private final Long userId;
    private String body;
    private Boolean answered;
    {
        id = sequence++;
    }
    public Question(Long userId, String body) {
        this.userId = userId;
        this.body = body;
        this.answered = false;
    }
}
