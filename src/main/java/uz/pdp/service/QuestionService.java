package uz.pdp.service;

import lombok.Getter;
import uz.pdp.model.Question;
import uz.pdp.repository.QuestionRepository;

import java.util.Optional;

public class QuestionService {
    @Getter
    private final static QuestionService instance = new QuestionService();
    private QuestionService(){}
    private final QuestionRepository questionsRepository = QuestionRepository.getInstance();
    public Boolean addQuestion(Question question){
        questionsRepository.save(question);
        return Boolean.TRUE;
    }

    public Question findByUserId(Long user){
        Optional<Question> byUserId = questionsRepository.findByUserId(user);
        return byUserId.orElse(null);
    }
    public Question findById(Integer question){
        Optional<Question> byId = questionsRepository.findById(question);
        return byId.orElse(null);
    }

    public void update(Question question) {
        questionsRepository.update(question.getId(),question);
    }

    public void delete(Integer id) {
        questionsRepository.delete(id);
    }
}
