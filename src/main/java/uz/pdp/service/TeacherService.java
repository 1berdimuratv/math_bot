package uz.pdp.service;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.model.Question;


public class TeacherService {
    @Getter
    private static final TeacherService instance = new TeacherService();
    private static final QuestionService questionService = QuestionService.getInstance();
    private TeacherService(){}


    public void forward(Update update) {

        if (update.hasMessage()) {
            Integer id;
            try {
                id = Integer.parseInt(update.getMessage().getCaption());
            }catch (Exception e) {
                ResService.sendErrorMsgForTeachers();
                return;
            }
            Question byId = questionService.findById(id);
            if (byId == null) {
                ResService.sendErrorMsgForTeachers();
                return;
            }
            ResService.forwardAnswerAndQuestion(byId.getBody(), update.getMessage().getVideo().getFileId());
            questionService.delete(id);
        }
    }
}
