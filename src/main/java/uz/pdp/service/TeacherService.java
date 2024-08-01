package uz.pdp.service;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.model.Question;


public class TeacherService {
    @Getter
    private static final TeacherService instance = new TeacherService();
    private static final QuestionService questionService = QuestionService.getInstance();
    private TeacherService(){}


    public void forward(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage() && (message.hasVideo() || message.hasPhoto())) {
            Integer id;
            try {
                id = Integer.parseInt(message.getCaption());
            }catch (Exception e) {
                ResService.sendErrorMsgForTeachers();
                return;
            }
            Question byId = questionService.findById(id);
            if (byId == null) {
                ResService.sendErrorMsgForTeachers();
                return;
            }
            String fileId;
            if (message.hasVideo())
                ResService.forwardFile(byId.getBody(), message.getVideo().getFileId(), true);
            else
                ResService.forwardFile(byId.getBody(), message.getPhoto().get(message.getPhoto().size() - 1).getFileId(), false);
            questionService.delete(id);
        }
    }
}
