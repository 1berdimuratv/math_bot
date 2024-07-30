package uz.pdp.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.model.User;
import uz.pdp.model.enams.UserState;
import uz.pdp.service.UserService;
import uz.pdp.utils.GlobalVar;


public class MyBotService {
    private final MyBot bot;
    private final UserService userService = UserService.getInstance();
    public MyBotService(MyBot bot) {this.bot = bot;}

    public void onUpdateReceived(Update update) throws Exception {
        GlobalVar.setMyBot(bot);
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
            message.setText(update.getCallbackQuery().getData());
        } else return;
        if (message.getText() != null && message.getText().equals("/start")){
            userService.userRestart(message.getChatId());
        }
        User user = userService.userVerify(message.getChatId());
        GlobalVar.setUSER(user);
        userCases(update, message, user.getUserState());
    }
    private void userCases(Update update, Message message, UserState userState) {
        switch (userState) {
            case USER_STARTED -> userService.started(message);
            case CHOOSE_LANGUAGE -> userService.setUserLang(update, message);
            case USERS_FIO -> userService.usersFIO(update, message);
            case MAIN_MENU -> userService.mainMenu(update,message);
            case SETTINGS -> userService.settings(update,message);
            case FEEDBACK -> userService.feedback(update,message);
            case CHANG_NUM -> userService.changing(update,message);
            case CHANG_NAM -> userService.changing(update,message);
            case ORDERS -> userService.orders(update,message);
            case MENU -> userService.menu(update,message);
            case ORDERING -> userService.getOrder(update,message);
            case SHARE_LOCATION -> userService.overOrder(update,message);
            case CHOOSING_AMOUNT -> userService.ordering_amount(update,message);
            default -> {
                return;
            }
        }
    }


}
