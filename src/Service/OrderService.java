package Service;

import Repository.Repository;
import Domain.*;

import java.util.List;

/**
 * Service class to manage order-related operations.
 */
public class OrderService {
    private final Repository<Order> orderRepository;

    /**
     * Constructs an OrderService with the specified repository.
     * @param orderRepository The repository for managing Order objects.
     */
    public OrderService(Repository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves an order by its unique ID.
     * @param id The unique ID of the order.
     * @return The Order object if found; otherwise, null.
     */
    public Order getOrder(Integer id) {
        return orderRepository.getByID(id);
    }

    /**
     * Retrieves all orders.
     * @return A list of all Order objects.
     */
    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    /**
     * Creates a new order.
     * @param order The Order object to be created.
     */
    public void createOrder(Order order) {
        orderRepository.create(order);
    }

    /**
     * Deletes an order by its unique ID.
     * @param id The unique ID of the order to delete.
     */
    public void deleteOrder(Integer id) {
        orderRepository.delete(id);
    }
}
