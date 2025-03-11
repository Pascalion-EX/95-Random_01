package com.example.service;

import com.example.model.Cart;
import com.example.model.User;
import com.example.model.Order;
import com.example.model.Product;

import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User> {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;


    @Autowired
    public UserService(UserRepository userRepository, ProductService productService, OrderRepository orderRepository, CartService cartService) {
        super();
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService=productService;
        this.repository = userRepository; // Assign repository from MainService
    }

    @Override
    protected UUID getId(User user) {
        return user.getId();
    }

    public User addUser(User user) {
        User savedUser = userRepository.addUser(user);
        Cart cart = new Cart(UUID.randomUUID(), savedUser.getId(), new ArrayList<>());
        cartService.addCart(cart);

        return savedUser;
    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }

    public void addOrderToUser(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);


        if (cart == null || cart.getProducts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty or not found, cannot place order.");
        }

        List<Product> products = new ArrayList<>(cart.getProducts());
        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();


        Order newOrder = new Order(UUID.randomUUID(), userId, totalPrice, products);
        orderRepository.addOrder(newOrder);
        userRepository.addOrderToUser(userId, newOrder);

        // Delete the cart after the order is successfully placed
        cartService.deleteCartById(cart.getId());
    }

    public void emptyCart(UUID userId){
        cartService.deleteCartById(userId);
    }


    public void removeOrderFromUser(UUID userId, UUID orderId) {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        boolean removed = user.getOrders().removeIf(order -> order.getId().equals(orderId));

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found in user's list");
        }

        userRepository.removeOrderFromUser(userId, orderId );
    }

    public void deleteUserById(UUID userId) {
        User user = userRepository.getUserById(userId);

        // Remove all associated orders
        for (Order order : user.getOrders()) {
            orderRepository.deleteOrderById(order.getId());
        }

        // Remove user's cart
        cartService.deleteCartById(userId);

        // Remove the user
        userRepository.deleteUserById(userId);
    }

    public void addProductToCart(UUID userId, UUID productId) {

            Cart cart = cartService.addCart(cartService.getCartByUserId(userId));
            Product product = productService.getProductById(productId); // Assuming you have a method to get the product by ID
            cartService.addProductToCart(cartService.getId(cart), product);


    }

    public void deleteProductFromCart(UUID userId, UUID productId) {
        User user = getUserById(userId);
        if (user != null) {
            Cart cart = cartService.getCartByUserId(userId);
            Product product = productService.getProductById(productId);
            if (product != null) {
                cartService.deleteProductFromCart(cart.getId(), product);
            }
        }
    }
}