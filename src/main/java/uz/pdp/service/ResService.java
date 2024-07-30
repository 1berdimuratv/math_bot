package uz.pdp.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.model.OrderProduct;
import uz.pdp.model.User;
import uz.pdp.utils.GlobalVar;
import uz.pdp.utils.MessageKey;

import java.util.List;

@Slf4j
public class ResService {
    private static final I18nService i18n = I18nService.getInstance();
    private static final Long me = -1002230976015L;   //-4266295619L;
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
    public static void sendFeedback(String feedback,String userFIO, String userPhone) {
        String text = """
                FIO : %s
                Number : %s
                Feedback : %s
                """.formatted(userFIO,userPhone,feedback);
        sendSimpleMsg(me, text);
    }
    public static void sendMsg( MessageKey text) {
        sendSimpleMsg(GlobalVar.getUSER().getChatId(), i18n.getMsg(text));
    }
    public static void sendErrorMsg() {
        sendMsg("Error!!");
    }


    public static void sendMsg( MessageKey key, ReplyKeyboard replyKeyboard) {
        sendMsg(i18n.getMsg(key), replyKeyboard);
    }



    public static void sendPhoto(MessageKey key, ReplyKeyboard replyKeyboard, InputFile inputFile) {
        try {
            GlobalVar.getMyBot().execute(
                    SendPhoto.builder()
                            .chatId(GlobalVar.getUSER().getChatId())
                            .caption(i18n.getMsg(key))
                            .photo(inputFile)
                            .replyMarkup(replyKeyboard)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }public static void sendPhoto(Long chatId, String card, ReplyKeyboard replyKeyboard, InputFile inputFile) {
        try {
            GlobalVar.getMyBot().execute(
                    SendPhoto.builder()
                            .chatId(chatId)
                            .caption(card)
                            .photo(inputFile)
                            .replyMarkup(replyKeyboard)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
    public static void changeButtons(Long chatId, ReplyKeyboard replyKeyboard){
        try {
            GlobalVar.getMyBot().execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .replyMarkup(replyKeyboard)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public static void sendOrderRequest(String s) {
        try {
            SendMessage build = SendMessage.builder()
                    .text(s)
                    .chatId(me)
                    .build();
            GlobalVar.getMyBot().execute(build);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
    public static void getLocation(String s, ReplyKeyboardMarkup replyKeyboard) {
        Long chatId = GlobalVar.getUSER().getChatId();
        try {
            GlobalVar.getMyBot().execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .replyMarkup(replyKeyboard)
                            .build()
            );
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
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
