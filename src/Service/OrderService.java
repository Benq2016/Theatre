package Service;

import Repository.Repository;
import Domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class OrderService {
    private final Repository<Order> orderRepository;

    public OrderService(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrder(Integer id) {
        return orderRepository.getByID(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public List<Order> getOrdersSorted() {
        List<Order> orders = orderRepository.getAll();
        List<Order> mutableOrders = new ArrayList<>(orders);

        mutableOrders.sort(Comparator.comparing(Order::getDate));
        return mutableOrders;
    }

    public List<Order> getOrdersFiltered() {
        LocalDate today = LocalDateTime.now().toLocalDate();
        List<Order> orders = orderRepository.getAll();
        return orders.stream().filter(order -> order.getDate().isAfter(today)).collect(Collectors.toList());
    }

    public void createOrder(Order order) {
        orderRepository.create(order);
    }

    public void deleteOrder(Integer id) {
        orderRepository.delete(id);
    }
}
