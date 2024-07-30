package uz.pdp.repository;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import uz.pdp.model.Category;
import uz.pdp.model.Product;
import uz.pdp.service.FileHelper;
import uz.pdp.utils.FileUrls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuProductRepository implements BaseRepository<Product,Integer>{
    @Getter
    private static final RuProductRepository instance = new RuProductRepository();

    private RuProductRepository() {
    }

    @Override
    public Boolean save(Product product) {
        return null;
    }

    @Override
    public Boolean update(Integer id, Product product) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return getAllProductsFromFile();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return getAllProductsFromFile().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
    public Optional<Product> findByIdInCategory(String prod) {
        return getAllProductsFromFile().stream()
                .filter(product -> product.getName().equals(prod))
                .findFirst();
    }
    public List<Product> findByCategory(String category) {
        return getAllProductsFromFile().stream()
                .filter(product -> product.getCategoryId().equals(category))
                .toList();
    }
    @NonNull
    private List<Product> getAllProductsFromFile() {
        List<Product> products = FileHelper.load(FileUrls.PRODUCTS_RU_URL, new TypeToken<List<Product>>(){}.getType());
        return products == null ? new ArrayList<>() : products;
    }
    private void setAllProductsFromFile(List<Product> products) {
        FileHelper.write(FileUrls.PRODUCTS_RU_URL, products);
    }
}
