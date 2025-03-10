package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService<Order> {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }

    @Override
    protected UUID getId(Order order) {
        return order.getId(); // Ensure `Order` class has a `getId()` method
    }

    // **Get all orders in the system**
    public ArrayList<Order> getOrders() {
        List<Order> orders = getAll(); // Using `getAll` from `MainService`
        return new ArrayList<>(orders); // Convert List to ArrayList
    }

    // **Get a specific order by ID**
    public Optional<Order> getOrderById(UUID orderId) {
        return getById(orderId); // Using `getById` from `MainService`
    }

    public void deleteOrderById(UUID orderId) throws IllegalArgumentException {
        Optional<Order> order = getOrderById(orderId);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
        delete(orderId); // Using `delete(UUID id)` from `MainService`
    }
}
