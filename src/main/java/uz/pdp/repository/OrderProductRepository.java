package uz.pdp.repository;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import uz.pdp.model.OrderProduct;
import uz.pdp.service.FileHelper;
import uz.pdp.utils.FileUrls;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderProductRepository implements BaseRepository<OrderProduct,Integer> {
    @Getter
    private static final OrderProductRepository instance = new OrderProductRepository();

    private OrderProductRepository(){}
    @Override
    public Boolean save(OrderProduct orderProduct) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        orders.add(orderProduct);
        setAllOrderProductsToFile(orders);
        return Boolean.TRUE;
    }
    public Boolean clearAll(Integer order) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        List<OrderProduct> res = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getOrderId().equals(order)){
                continue;
            }
            res.add(orders.get(i));
        }
        setAllOrderProductsToFile(res);
        return Boolean.TRUE;
    }

    @Override
    public Boolean update(Integer id, OrderProduct orderProduct) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        for (OrderProduct temp : orders) {
            if (temp.getId().equals(id)) {
                temp.setProductId(orderProduct.getProductId());
                temp.setAmount(orderProduct.getAmount());
                temp.setOrderId(orderProduct.getOrderId());
                temp.setSubmitted(orderProduct.getSubmitted());
                break;
            }
        }
        setAllOrderProductsToFile(orders);
        return true;
    }

    @Override
    public List<OrderProduct> findAll() {
        return null;
    }
    @Override
    public Optional<OrderProduct> findById(Integer id) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        for (OrderProduct order : orders) {
            if (order.getId().equals(id))
                return Optional.of(order);
        }
        return Optional.empty();
    }
    public List<OrderProduct> findByOrderId(Integer id) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        var res = new ArrayList<OrderProduct>();
        for (OrderProduct order : orders) {
            if (order.getOrderId().equals(id))
                res.add(order);
        }
        return res;
    }
    @NonNull
    private List<OrderProduct> getAllOrderProductsFromFile() {
        List<OrderProduct> orders = FileHelper.load(FileUrls.ORDER_PRODUCT_URL, new TypeToken<List<OrderProduct>>(){}.getType());
        return orders == null ? new ArrayList<>() : orders;
    }

    private void setAllOrderProductsToFile(List<OrderProduct> users) {
        FileHelper.write(FileUrls.ORDER_PRODUCT_URL, users);
    }

    public Boolean deleteProd(Integer order, Integer prod) {
        List<OrderProduct> orders = getAllOrderProductsFromFile();
        for (int i = 0; i < orders.size(); i++) {
                if(orders.get(i).getOrderId().equals(order) && Objects.equals(orders.get(i).getProductId(), prod)){
                    orders.remove(i);
                    setAllOrderProductsToFile(orders);
                    return Boolean.TRUE;
                }
            }
        return Boolean.FALSE;
    }
}
