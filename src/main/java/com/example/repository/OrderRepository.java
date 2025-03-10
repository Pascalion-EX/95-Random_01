package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {

    public OrderRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/resources/orders.json";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }



    public ArrayList<Order> getOrders() {
        return findAll();
    }


    public void addOrder(Order order) {
        ArrayList<Order> orders = findAll();
        orders.add(order);
        saveAll(orders);
    }


    public Order getOrderById(UUID orderId) {
        return findAll().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();
        orders.removeIf(order -> order.getId().equals(orderId));
        saveAll(orders);
    }




}
