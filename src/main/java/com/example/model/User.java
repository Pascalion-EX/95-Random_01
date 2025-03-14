package com.example.model;

import org.springframework.stereotype.Component;
import com.example.model.Cart;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class User {
    Cart cart;
    private UUID id;
    private String name;
    private List<Order> orders = new ArrayList<>();

    // Default Constructor
    public User() {
        this.id = UUID.randomUUID();
    }

    // Constructor with name
    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.orders = new ArrayList<>();
    }

    // Constructor with all fields
    public User(UUID id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;


    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
