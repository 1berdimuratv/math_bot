package uz.pdp.service;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.model.*;
import uz.pdp.model.enams.Lang;
import uz.pdp.model.enams.UserState;
import uz.pdp.utils.CoreUtils;
import uz.pdp.utils.GlobalVar;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static uz.pdp.utils.MessageKey.*;

public class UserService {
    @Getter
    private static final UserService instance = new UserService();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final QuestionService questionService = QuestionService.getInstance();
    private static I18nService i18n = I18nService.getInstance();
    private UserService(){}

    public void mainMenu(Update update, Message message) {
        User user = GlobalVar.getUSER();
        if (!switchMenu(message,user)) {
            ResService.sendErrorMsg();
        }
        userRepository.update(user.getChatId(),user);
        switch (user.getUserState()){
            case MENU -> {
                List<Category> all = categoryService.findAll(user.getLang());
                ResService.sendMsg(ORDER_MENU,ButtonService.back());
            }
            case FEEDBACK -> {
                ResService.sendMsg(ASK_OPINION,ButtonService.back());
            }
            case SETTINGS -> {
                ResService.sendMsg(SETTINGS,ButtonService.settings(user.getFio(),user.getPhoneNumber()));
            }
        }
    }

    public void question(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String text = message.getText();
        Long userId = user.getChatId();
        if (text.equals(i18n.getMsg(BACK))){
            user.setUserState(UserState.MAIN_MENU);
            userRepository.update(userId,user);
            ResService.sendMsg(MAIN_MENU, ButtonService.mainMenu());
            return;
        }
        Question question = new Question(userId,text);
        questionService.addQuestion(question);
        ResService.sendQuestion(question,user);
        user.setUserState(UserState.MAIN_MENU);
        userRepository.update(user.getChatId(),user);
        ResService.sendMsg(ORDER_ACCEPTED,ButtonService.mainMenu());
        user.setUserState(UserState.MAIN_MENU);
        userRepository.update(userId,user);
    }

    public void feedback(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String text = message.getText();
        if (Objects.equals(text,i18n.getMsg(BACK)))
            ResService.sendMsg(MAIN_MENU, ButtonService.mainMenu());
        else {
            ResService.sendFeedback(text,user.getFio(),user.getPhoneNumber());
            ResService.sendMsg(TAKE_OPINION,ButtonService.mainMenu());
        }
        user.setUserState(UserState.MAIN_MENU);
        userRepository.update(user.getChatId(),user);
    }
    public void settings(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String text = message.getText();
        if (Objects.equals(text,i18n.getMsg(CHANGE_LANG))) {
            user.setUserState(UserState.CHOOSE_LANGUAGE);
            userRepository.update(user.getChatId(), user);
            ResService.sendMsg(CHOOSE_LANG, ButtonService.chooseLanguage());
        }else if (Objects.equals(text,i18n.getMsg(CHANGE_NAME).formatted(user.getFio()))) {
            user.setUserState(UserState.CHANG_NAM);
            userRepository.update(user.getChatId(), user);
            ResService.sendMsg( USERS_FIO);
        }else if (Objects.equals(text,i18n.getMsg(BACK))){
            user.setUserState(UserState.MAIN_MENU);
            userRepository.update(user.getChatId(), user);
            ResService.sendMsg(MAIN_MENU, ButtonService.mainMenu());
        }
    }
    public void userRestart(Long chatId){
        Optional<User> byId = userRepository.findById(chatId);
        if (byId.isPresent()){
            userRepository.delete(chatId);
        }
    }

    public User userVerify(Long chatId) {
        Optional<User> optional = userRepository.findById(chatId);
        if (optional.isEmpty()) {
            User user = User.builder()
                    .chatId(chatId)
                    .userState(UserState.USER_STARTED)
                    .lang(Lang.UZ)
                    .build();
            userRepository.save(user);
            return user;
        }
        return optional.get();
    }

    public void started( Message message) {
        User user = GlobalVar.getUSER();
        if(     !message.hasText() ||
                !message.getText().equals("/start") ) {
            try {
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(GlobalVar.getUSER().getChatId());
                deleteMessage.setMessageId(message.getMessageId());
                GlobalVar.getMyBot().execute(deleteMessage);
                return;
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        user.setUserState(UserState.CHOOSE_LANGUAGE);
        userRepository.update(user.getChatId(), user);
        ResService.sendMsg(STARTED_MSG, ButtonService.chooseLanguage());
    }

    public void usersFIO(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String name = message.getText();
        if (name == null || !CoreUtils.checkNameFormat(name,user.getLang())){
            ResService.sendMsg(NAME_ERROR);
            return;
        }
        user.setFio(name);
        user.setUserState(UserState.MAIN_MENU);
        userRepository.update(user.getChatId(), user);

        ResService.sendMsg(MAIN_MENU,ButtonService.mainMenu());
    }


        public void setUserLang(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String text = message.getText();

        if (!setLang(text, user)) {
            ResService.sendErrorMsg();
            return;
        }
        if (user.getFio()!=null){
            user.setUserState(UserState.SETTINGS);
            userRepository.update(user.getChatId(),user);
            ResService.sendMsg(SUCCESSFUL);
            ResService.sendMsg(SETTINGS, ButtonService.settings(user.getFio(),user.getPhoneNumber()));
            return;
        }
        user.setUserState(UserState.USERS_FIO);
        userRepository.update(user.getChatId(), user);

        ResService.sendMsg(USERS_FIO);
    }

    private boolean setLang(String text, User user) {
        if (Objects.equals(text, LANG_UZ.getVal()))
            user.setLang(Lang.UZ);
        else if (Objects.equals(text, LANG_RU.getVal()))
            user.setLang(Lang.RU);
        else
            return false;
        return true;
    }

    private boolean switchMenu(Message message, User user) {
        String text = message.getText();
        if (Objects.equals(text,i18n.getMsg(MENU))){
            user.setUserState(UserState.MENU);
        }else if (Objects.equals(text,i18n.getMsg(ORDERS))){
            user.setUserState(UserState.ORDERS);
        } else if (Objects.equals(text,i18n.getMsg(SETTINGS))) {
            user.setUserState(UserState.SETTINGS);
        } else if (Objects.equals(text,i18n.getMsg(FEEDBACK))) {
            user.setUserState(UserState.FEEDBACK);
        } else
            return false;
        return true;
    }

    public void changing(Update update, Message message) {
        User user = GlobalVar.getUSER();
        String name = message.getText();
        if (!CoreUtils.checkNameFormat(name, user.getLang())) {
            ResService.sendErrorMsg();
            return;
        }
        user.setFio(name);
        user.setUserState(UserState.SETTINGS);
        userRepository.update(user.getChatId(), user);
        ResService.sendMsg(SUCCESSFUL);
        ResService.sendMsg(SETTINGS, ButtonService.settings(user.getFio(), user.getPhoneNumber()));
    }
}
