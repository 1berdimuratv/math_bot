package uz.pdp.repository;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import uz.pdp.model.Question;
import uz.pdp.service.FileHelper;
import uz.pdp.utils.FileUrls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionRepository implements BaseRepository<Question, Integer> {
        @Getter
        private static final QuestionRepository instance = new QuestionRepository();
        private QuestionRepository() {}

        @Override
        public Boolean save(Question question) {
            List<Question> questions = getAllFromFile();
            questions.add(question);
            setAllToFile(questions);
            return Boolean.TRUE;
        }

        @Override
        public Boolean update(Integer id, Question question) {
            List<Question> questions = getAllFromFile();
            for (Question temp : questions) {
                if (temp.getId().equals(id)) {
                    temp.setAnswered(question.getAnswered());
                    temp.setBody(question.getBody());
                    break;
                }
            }
            setAllToFile(questions);
            return true;
        }

        public Boolean delete(Integer question){
            List<Question> questions = getAllFromFile();
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getId().equals(question)){
                    questions.remove(i);
                    setAllToFile(questions);
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        @Override
        public List<Question> findAll() {
            return null;
        }

        @Override
        public Optional<Question> findById(Integer id) {
            List<Question> questions = getAllFromFile();
            for (Question question : questions) {
                if (question.getId().equals(id))
                    return Optional.of(question);
            }
            return Optional.empty();
        }
        public Optional<Question> findByUserId(Long id) {
            List<Question> questions = getAllFromFile();
            for (Question question : questions) {
                if (question.getUserId().equals(id))
                    return Optional.of(question);
            }
            return Optional.empty();
        }

        @NonNull
        private List<Question> getAllFromFile() {
            List<Question> questions = FileHelper.load(FileUrls.QUESTIONS_URL, new TypeToken<List<Question>>(){}.getType());
            return questions == null ? new ArrayList<>() : questions;
        }
        private void setAllToFile(List<Question> questions) {
            FileHelper.write(FileUrls.QUESTIONS_URL, questions);
        }
}
