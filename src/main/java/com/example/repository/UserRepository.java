package com.example.repository;

import com.example.model.*;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.repository.CartRepository;
import com.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {

    private final CartRepository cartRepository;

    public UserRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class; // Type reference for deserialization
    }

    public ArrayList<User> getUsers() {
        return findAll(); // Retrieve all users from JSON
    }

    public User getUserById(UUID userId) {
        return findAll().stream()
                .filter(user -> userId.equals(user.getId())) // Direct comparison of UUIDs
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User addUser(User user) {
        ArrayList<User> users = findAll();
        users.add(user);
        saveAll(users);
        Cart cart = cartRepository.addCart(new Cart(user.getId()));


        return user;
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return findAll().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .map(User::getOrders)
                .orElse(new ArrayList<>());
    }

    public void addOrderToUser(UUID userId, Order order) {
        List<User> users = findAll();

        users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    user.getOrders().add(order);
                    saveAll(new ArrayList<>(users));
                });
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        List<User> users = findAll();

        users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    user.getOrders().removeIf(order -> order.getId().equals(orderId));
                    saveAll(new ArrayList<>(users));
                });
    }

    public void deleteUserById(UUID userId) {
        List<User> users = findAll();
        users.removeIf(user -> user.getId().equals(userId));
        saveAll(new ArrayList<>(users));
    }



}
