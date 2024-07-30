package uz.pdp.service;

import com.google.gson.reflect.TypeToken;
import uz.pdp.utils.FileUrls;
import uz.pdp.utils.GlobalVar;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileHelper {
    static {
        File file = Path.of(FileUrls.USER_URL).toFile();
        File file2 = Path.of(FileUrls.ORDER_PRODUCT_URL).toFile();
        File file3 = Path.of(FileUrls.ORDER_URL).toFile();
        try {
            file.delete();
            file2.delete();
            file3.delete();
            file.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> List<E> load(String url, Type type) {
        try {
            byte[] bytes = Files.readAllBytes(Path.of(url));
            String json = new String(bytes);
            return GlobalVar.GSON.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <E> void write(String url, List<E> data) {
        try {
            String json = GlobalVar.GSON.toJson(data);
            Files.writeString(Path.of(url), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
