package uz.pdp.service;

import lombok.Getter;
import uz.pdp.model.Category;
import uz.pdp.model.enams.Lang;
import uz.pdp.repository.RuCategoryRepository;
import uz.pdp.repository.UzCategoryRepository;

import java.util.List;

public class CategoryService {
    @Getter
    private static final CategoryService instance = new CategoryService();
    private CategoryService() {}
    private final UzCategoryRepository uzCategoryRepository = UzCategoryRepository.getInstance();
    private final RuCategoryRepository ruCategoryRepository = RuCategoryRepository.getInstance();

    public List<Category> findAll(Lang lang) {
        if (lang == Lang.RU)
            return ruCategoryRepository.findAll();
        return uzCategoryRepository.findAll();
    }
    public Category find(String name,Lang lang) {
        if (lang == Lang.RU)
            return ruCategoryRepository.findById(name).get();
        else
            return uzCategoryRepository.findById(name).get();

    }

}
