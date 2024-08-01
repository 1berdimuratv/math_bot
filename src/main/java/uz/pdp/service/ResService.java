package uz.pdp.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.bot.BotProperty;
import uz.pdp.model.Question;
import uz.pdp.model.User;
import uz.pdp.model.enams.Lang;
import uz.pdp.utils.GlobalVar;
import uz.pdp.utils.MessageKey;

@Slf4j
public class ResService {
    private static final I18nService i18n = I18nService.getInstance();
    private static final String feedback = """
                #FIKR_ОТЗЫВ
                
                F.I.O. : %s
                Habar/Соощение : %s
                """;
    private static final String question = """
                #SAVOL_ВОПРОС
                
                ID : %s
                F.I.O. : %s
                Savol/Вопрос : %s
                """;
    private static final String answer = """
                #JAVOB
                
                Savol/Вопрос : %s
                
                Agar qo'shimcha savollaringiz bo'lsa yoki boshqa biror narsa
                bo'yicha yordam kerak bo'lsa, @berdimuratvs_bot bilan
                bog'laning!☺️
                """;
    public static void sendMsg(String text, ReplyKeyboard replyKeyboard) {
        try {
                GlobalVar.getMyBot().execute(
                        SendMessage.builder()
                                .text(text)
                                .chatId(GlobalVar.getUSER().getChatId())
                                .replyMarkup(replyKeyboard)
                                .build()
                );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
    private static void sendSimpleMsg(Long chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            GlobalVar.getMyBot().execute(
                       message
                );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    public static void sendMsg(String text) {
        sendSimpleMsg(GlobalVar.getUSER().getChatId(), text);
    }
    public static void sendFeedback(String feedbackObj,String userFIO) {
        String text = feedback.formatted(userFIO,feedbackObj);
        sendSimpleMsg(BotProperty.TEACHERS, text);
    }
    public static void sendMsg( MessageKey text) {
        sendSimpleMsg(GlobalVar.getUSER().getChatId(), i18n.getMsg(text));
    }
    public static void sendErrorMsg() {
        sendMsg("Error!!");
    }
    public static void sendErrorMsgForTeachers() {
        sendSimpleMsg(BotProperty.TEACHERS,"Error!!");
    }


    public static void sendMsg( MessageKey key, ReplyKeyboard replyKeyboard) {
        sendMsg(i18n.getMsg(key), replyKeyboard);
    }

    public static void sendQuestion(Question questionObj,User user) {
        StringBuilder res = new StringBuilder();
        Lang lang = user.getLang();
        String s = question.formatted(questionObj.getId(),user.getFio(),questionObj.getBody())+" \n";
        try {
            SendMessage build = SendMessage.builder()
                    .text(s)
                    .chatId(BotProperty.TEACHERS)
                    .build();
            GlobalVar.getMyBot().execute(build);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
    public static void forwardFile(String questionObj,String file, boolean isVideo) {
        InputFile inputFile = new InputFile(file);
        String caption = answer.formatted(questionObj);
        if (isVideo) {
            try {
                SendVideo build = SendVideo.builder()
                        .chatId(BotProperty.CHANEL)
                        .video(inputFile)
                        .caption(caption)
                        .build();
                GlobalVar.getMyBot().execute(build);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }else {
            try {
                SendPhoto build = SendPhoto.builder()
                        .chatId(BotProperty.CHANEL)
                        .photo(inputFile)
                        .caption(caption)
                        .build();
                GlobalVar.getMyBot().execute(build);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static void deleteMsg(Integer message) {
        try {
            GlobalVar.getMyBot().execute(
                    DeleteMessage.builder()
                            .chatId(GlobalVar.getUSER().getChatId())
                            .messageId(message)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
