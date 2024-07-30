package uz.pdp.service;

import lombok.Getter;
import uz.pdp.model.Category;
import uz.pdp.model.Product;
import uz.pdp.model.enams.Lang;
import uz.pdp.repository.RuProductRepository;
import uz.pdp.repository.UzProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {
    @Getter
    private static final ProductService instance = new ProductService();
    private ProductService() {}
    private final RuProductRepository ruProductRepository = RuProductRepository.getInstance();
    private final UzProductRepository uzProductRepository = UzProductRepository.getInstance();

    public List<Product> findAll(Lang lang) {
        if (lang == Lang.RU)
            return ruProductRepository.findAll();
        return uzProductRepository.findAll();
    }
    public List<Product> findByCategory(String category,Lang lang) {
        if (lang == Lang.RU)
            return ruProductRepository.findByCategory(category);
        else
            return uzProductRepository.findByCategory(category);

    }
    public Optional<Product> findByName(String product, Lang lang) {
        if (lang == Lang.RU)
            return ruProductRepository.findByIdInCategory(product);
        else
            return uzProductRepository.findByIdInCategory(product);

    }
    public Optional<Product> findById(Integer product, Lang lang) {
        if (lang == Lang.RU)
            return ruProductRepository.findById(product);
        else
            return uzProductRepository.findById(product);

    }

}
