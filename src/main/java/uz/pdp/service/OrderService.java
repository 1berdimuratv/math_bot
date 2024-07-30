package uz.pdp.service;

import lombok.Getter;
import uz.pdp.model.Order;
import uz.pdp.model.OrderProduct;
import uz.pdp.model.Product;
import uz.pdp.model.User;
import uz.pdp.model.enams.Lang;
import uz.pdp.repository.OrderProductRepository;
import uz.pdp.repository.OrdersRepository;
import uz.pdp.utils.GlobalVar;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class OrderService {
    @Getter
    private final static OrderService instance = new OrderService();
    private OrderService(){}
    private final OrdersRepository ordersRepository = OrdersRepository.getInstance();
    private final OrderProductRepository orderProductRepository = OrderProductRepository.getInstance();
    private final ProductService productService = ProductService.getInstance();
    public Boolean addOrder(Integer amount,Long user, Product product){
        Order order1;
        Optional<Order> byId = ordersRepository.findByUserId(user);
        if (byId.isEmpty()){
            order1 = new Order(user,amount * product.getPrice());
            ordersRepository.save(order1);
        }
        else {
            order1 = byId.get();
            order1.setActualPrice(order1.getActualPrice() + (amount * product.getPrice()));
        }
        OrderProduct orderProd = new OrderProduct(product.getId(), amount, order1.getId());
        orderProd.setSubmitted(Boolean.TRUE);
        orderProductRepository.save(orderProd);
        ordersRepository.update(order1.getId(),order1);
        return Boolean.TRUE;
    }

    public OrderProduct getOrderProductIdByUser(Long user) {
        Integer order = findByUserId(user).getId();
        List<OrderProduct> byUserId = orderProductRepository.findByOrderId(order);
        Optional<OrderProduct> first = byUserId.stream().filter(orderP -> !orderP.getSubmitted()).findFirst();
        OrderProduct orderProd = first.get();
        return orderProd;
    }

    public Boolean clearAll(Long user){
        Integer order = findByUserId(user).getId();
        orderProductRepository.clearAll(order);
        return ordersRepository.delete(order);
    }
    public Order findByUserId(Long user){
        Optional<Order> byUserId = ordersRepository.findByUserId(user);
        return byUserId.orElse(null);
    }
    public List<OrderProduct> getAllByUser(Long user){
        Optional<Order> byUserId = ordersRepository.findByUserId(user);
        if (byUserId.isEmpty()) {
            return Collections.emptyList();
        }
        Order order = byUserId.get();
        return orderProductRepository.findByOrderId(order.getId());
    }

    public void clearProd(Long chatId, Integer prod) {
        Integer order = findByUserId(chatId).getId();
        orderProductRepository.deleteProd(order,prod);
    }

    public void update(Order order) {
        ordersRepository.update(order.getId(),order);
    }
}
