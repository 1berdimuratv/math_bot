package uz.pdp.repository;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import uz.pdp.model.Order;
import uz.pdp.model.User;
import uz.pdp.service.FileHelper;
import uz.pdp.utils.FileUrls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersRepository implements BaseRepository<Order, Integer> {
        @Getter
        private static final OrdersRepository instance = new OrdersRepository();
        private OrdersRepository() {}

        @Override
        public Boolean save(Order order) {
            List<Order> orders = getAllOrdersFromFile();
            orders.add(order);
            setAllOrdersToFile(orders);
            return Boolean.TRUE;
        }

        @Override
        public Boolean update(Integer id, Order order) {
            List<Order> orders = getAllOrdersFromFile();
            for (Order temp : orders) {
                if (temp.getId().equals(id)) {
                    temp.setActualPrice(order.getActualPrice());
                    temp.setUserId(order.getUserId());
                    temp.setDeliveryPrice(order.getDeliveryPrice());
                    break;
                }
            }
            setAllOrdersToFile(orders);
            return true;
        }
        public Boolean delete(Integer order1){
            List<Order> orders = getAllOrdersFromFile();
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getId().equals(order1)){
                    orders.remove(i);
                    setAllOrdersToFile(orders);
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        @Override
        public List<Order> findAll() {
            return null;
        }

        @Override
        public Optional<Order> findById(Integer id) {
            List<Order> orders = getAllOrdersFromFile();
            for (Order order : orders) {
                if (order.getId().equals(id))
                    return Optional.of(order);
            }
            return Optional.empty();
        }
        public Optional<Order> findByUserId(Long id) {
            List<Order> orders = getAllOrdersFromFile();
            for (Order order : orders) {
                if (order.getUserId().equals(id))
                    return Optional.of(order);
            }
            return Optional.empty();
        }

        @NonNull
        private List<Order> getAllOrdersFromFile() {
            List<Order> orders = FileHelper.load(FileUrls.ORDER_URL, new TypeToken<List<Order>>(){}.getType());
            return orders == null ? new ArrayList<>() : orders;
        }
        private void setAllOrdersToFile(List<Order> orders) {
            FileHelper.write(FileUrls.ORDER_URL, orders);
        }
}
