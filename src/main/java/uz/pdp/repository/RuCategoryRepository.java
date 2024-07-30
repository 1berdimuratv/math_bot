package uz.pdp.repository;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import uz.pdp.model.Category;
import uz.pdp.service.FileHelper;
import uz.pdp.utils.FileUrls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuCategoryRepository implements BaseRepository<Category, String> {
    @Getter
    private static final RuCategoryRepository instance = new RuCategoryRepository();
    private RuCategoryRepository() {}


    @Override
    public Boolean save(Category category) {
        return null;
    }

    @Override
    public Boolean update(String id, Category category) {
        List<Category> categories = getAllCategoriesFromFile();
        for (Category category1 : categories) {
            if (category1.getName().equals(id)){
                category1.setUrl(category.getUrl());
                category1.setNumber(category.getNumber());
                setAllCategoriesFromFile(categories);
                break;
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public List<Category> findAll() {
        return getAllCategoriesFromFile();
    }

    @Override
    public Optional<Category> findById(String id) {
        return getAllCategoriesFromFile().stream()
                .filter(category -> category.getName().equals(id))
                .findFirst();
    }
    @NonNull
    private List<Category> getAllCategoriesFromFile() {
        List<Category> categories = FileHelper.load(FileUrls.CATEGORY_RU_URL, new TypeToken<List<Category>>(){}.getType());
        return categories == null ? new ArrayList<>() : categories;
    }
    private void setAllCategoriesFromFile(List<Category> categories) {
        FileHelper.write(FileUrls.CATEGORY_RU_URL, categories);
    }
}
