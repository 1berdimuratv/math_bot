package uz.pdp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Question {
    private final Integer id;
    private static int sequence = 0;
    private final Long userId;
    private String body;
    {
        id = sequence++;
    }
    public Question(Long userId, String body) {
        this.userId = userId;
        this.body = body;
    }
}
