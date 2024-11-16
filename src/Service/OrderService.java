package Service;

import Repository.Repository;
import Domain.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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

    public void createOrder(Order order) {
        orderRepository.create(order);
    }

    public boolean deleteOrder(Integer id) {
        if (getOrder(id) == null)
            return false;
        orderRepository.delete(id);
        return true;
    }
}
