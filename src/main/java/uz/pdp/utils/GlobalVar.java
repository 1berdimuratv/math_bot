package uz.pdp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import uz.pdp.bot.MyBot;
import uz.pdp.model.Category;
import uz.pdp.model.OrderProduct;
import uz.pdp.model.Product;
import uz.pdp.model.User;

public class GlobalVar {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private static final ThreadLocal<User> USER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<MyBot> MY_BOT = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Category> CATEGORY = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Product> PRODUCT = ThreadLocal.withInitial(() -> null);

    public static User getUSER() {
        return USER.get();
    }
    public static Category getCATEGORY() {
        return CATEGORY.get();
    }
    public static Product getPRODUCT() {
        return PRODUCT.get();
    }
    public static void setUSER(User user) {
        USER.set(user);
    }
    public static void setCATEGORY(Category category) {
        CATEGORY.set(category);
    }
    public static void setPRODUCT(Product product) {
        PRODUCT.set(product);
    }
    public static MyBot getMyBot() {
        return MY_BOT.get();
    }

    public static void setMyBot(MyBot myBot) {
        MY_BOT.set(myBot);
    }
}
