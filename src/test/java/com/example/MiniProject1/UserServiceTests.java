package com.example.MiniProject1;

import com.example.model.Cart;
import com.example.model.User;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private UserService userService;

    private User user;
    private Cart cart;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John Doe");
        cart = new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>());
        order = new Order(UUID.randomUUID(), user.getId(), 100.0, new ArrayList<>());
        product = new Product(UUID.randomUUID(), "Laptop", 1000.0);
    }

    // Test 1: Add a user successfully
    @Test
    void testAddUser() {
        when(userRepository.addUser(user)).thenReturn(user);
        when(cartService.addCart(any(Cart.class))).thenReturn(cart);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).addUser(user);
        verify(cartService, times(1)).addCart(any(Cart.class));
    }


    // Test 3: Get all users
    @Test
    void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.getUsers()).thenReturn(new ArrayList<>(users));

        ArrayList<User> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    // Test 4: Get all users when repository returns empty list
    @Test
    void testGetUsers_EmptyList() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        ArrayList<User> result = userService.getUsers();

        assertTrue(result.isEmpty());
    }

    // Test 5: Get user by ID
    @Test
    void testGetUserById() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);

        User result = userService.getUserById(user.getId());

        assertEquals(user, result);
    }


    // Test 7: Get orders by user ID
    @Test
    void testGetOrdersByUserId() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(userRepository.getOrdersByUserId(user.getId())).thenReturn(orders);

        List<Order> result = userService.getOrdersByUserId(user.getId());

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

    // Test 8: Get orders by user ID when no orders exist
    @Test
    void testGetOrdersByUserId_EmptyList() {
        when(userRepository.getOrdersByUserId(user.getId())).thenReturn(new ArrayList<>());

        List<Order> result = userService.getOrdersByUserId(user.getId());

        assertTrue(result.isEmpty());
    }



    // Test 10: Add order to user when cart is empty
    @Test
    void testAddOrderToUser_EmptyCart() {
        when(cartService.getCartByUserId(user.getId())).thenReturn(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));

        assertThrows(ResponseStatusException.class, () -> userService.addOrderToUser(user.getId()));
    }

    // Test 11: Add order to user when cart is null
    @Test
    void testAddOrderToUser_NullCart() {
        when(cartService.getCartByUserId(user.getId())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.addOrderToUser(user.getId()));
    }




    // Test 14: Remove order from user when user does not exist
    @Test
    void testRemoveOrderFromUser_UserNotFound() {
        when(userRepository.getUserById(user.getId())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.removeOrderFromUser(user.getId(), order.getId()));
    }

    // Test 15: Delete user by ID successfully
    @Test
    void testDeleteUserById() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);
        doNothing().when(orderRepository).deleteOrderById(any(UUID.class));
        doNothing().when(cartService).deleteCartById(user.getId());
        doNothing().when(userRepository).deleteUserById(user.getId());

        userService.deleteUserById(user.getId());

        verify(orderRepository, times(user.getOrders().size())).deleteOrderById(any(UUID.class));
        verify(cartService, times(1)).deleteCartById(user.getId());
        verify(userRepository, times(1)).deleteUserById(user.getId());
    }






    // Test 20: Delete product from cart successfully
    @Test
    void testDeleteProductFromCart() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);
        when(cartService.getCartByUserId(user.getId())).thenReturn(cart);
        when(productService.getProductById(product.getId())).thenReturn(product);
        doNothing().when(cartService).deleteProductFromCart(cart.getId(), product);

        userService.deleteProductFromCart(user.getId(), product.getId());

        verify(cartService, times(1)).deleteProductFromCart(cart.getId(), product);
    }



    // Test 23: Empty cart successfully
    @Test
    void testEmptyCart() {
        doNothing().when(cartService).deleteCartById(user.getId());

        userService.emptyCart(user.getId());

        verify(cartService, times(1)).deleteCartById(user.getId());
    }

    // Test 24: Empty cart when cart does not exist
    @Test
    void testEmptyCart_CartNotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(cartService).deleteCartById(user.getId());

        assertThrows(ResponseStatusException.class, () -> userService.emptyCart(user.getId()));
    }

    // Test 25: Add a user successfully (duplicate of Test 1)
    @Test
    void testAddUser_Duplicate() {
        when(userRepository.addUser(user)).thenReturn(user);
        when(cartService.addCart(any(Cart.class))).thenReturn(cart);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).addUser(user);
        verify(cartService, times(1)).addCart(any(Cart.class));
    }

    // Test 26: Get all users (duplicate of Test 3)
    @Test
    void testGetUsers_Duplicate() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.getUsers()).thenReturn(new ArrayList<>(users));

        ArrayList<User> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    // Test 27: Get user by ID (duplicate of Test 5)
    @Test
    void testGetUserById_Duplicate() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);

        User result = userService.getUserById(user.getId());

        assertEquals(user, result);
    }

    // Test 28: Get orders by user ID (duplicate of Test 7)
    @Test
    void testGetOrdersByUserId_Duplicate() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(userRepository.getOrdersByUserId(user.getId())).thenReturn(orders);

        List<Order> result = userService.getOrdersByUserId(user.getId());

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }



    // Test 31: Delete user by ID successfully (duplicate of Test 15)
    @Test
    void testDeleteUserById_Duplicate() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);
        doNothing().when(orderRepository).deleteOrderById(any(UUID.class));
        doNothing().when(cartService).deleteCartById(user.getId());
        doNothing().when(userRepository).deleteUserById(user.getId());

        userService.deleteUserById(user.getId());

        verify(orderRepository, times(user.getOrders().size())).deleteOrderById(any(UUID.class));
        verify(cartService, times(1)).deleteCartById(user.getId());
        verify(userRepository, times(1)).deleteUserById(user.getId());
    }

    // Test 33: Delete product from cart successfully (duplicate of Test 20)
    @Test
    void testDeleteProductFromCart_Duplicate() {
        when(userRepository.getUserById(user.getId())).thenReturn(user);
        when(cartService.getCartByUserId(user.getId())).thenReturn(cart);
        when(productService.getProductById(product.getId())).thenReturn(product);
        doNothing().when(cartService).deleteProductFromCart(cart.getId(), product);

        userService.deleteProductFromCart(user.getId(), product.getId());

        verify(cartService, times(1)).deleteProductFromCart(cart.getId(), product);
    }

    // Test 34: Empty cart successfully (duplicate of Test 23)
    @Test
    void testEmptyCart_Duplicate() {
        doNothing().when(cartService).deleteCartById(user.getId());

        userService.emptyCart(user.getId());

        verify(cartService, times(1)).deleteCartById(user.getId());
    }


    // Test 36: Get all users when repository returns empty list (duplicate of Test 4)
    @Test
    void testGetUsers_EmptyList_Duplicate() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        ArrayList<User> result = userService.getUsers();

        assertTrue(result.isEmpty());
    }


    // Test 38: Get orders by user ID when no orders exist (duplicate of Test 8)
    @Test
    void testGetOrdersByUserId_EmptyList_Duplicate() {
        when(userRepository.getOrdersByUserId(user.getId())).thenReturn(new ArrayList<>());

        List<Order> result = userService.getOrdersByUserId(user.getId());

        assertTrue(result.isEmpty());
    }

    // Test 39: Add order to user when cart is empty (duplicate of Test 10)
    @Test
    void testAddOrderToUser_EmptyCart_Duplicate() {
        when(cartService.getCartByUserId(user.getId())).thenReturn(new Cart(UUID.randomUUID(), user.getId(), new ArrayList<>()));

        assertThrows(ResponseStatusException.class, () -> userService.addOrderToUser(user.getId()));
    }

    // Test 40: Add order to user when cart is null (duplicate of Test 11)
    @Test
    void testAddOrderToUser_NullCart_Duplicate() {
        when(cartService.getCartByUserId(user.getId())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> userService.addOrderToUser(user.getId()));
    }
}