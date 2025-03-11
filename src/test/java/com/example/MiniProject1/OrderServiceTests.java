package com.example.MiniProject1;


import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId(UUID.randomUUID());
    }

    @Test
    void testAddOrder() {
        // Arrange
        doNothing().when(orderRepository).addOrder(order);

        // Act
        orderService.addOrder(order);

        // Assert
        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated() {
        // Arrange
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        // Act
        orderService.addOrder(newOrder);

        // Assert
        assertNotNull(newOrder.getId());
    }
    @Test
    void testAddOrder_Duplicate1() {
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated_Duplicate1() {
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        orderService.addOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

    @Test
    void testAddOrder_Duplicate2() {
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated_Duplicate2() {
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        orderService.addOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

    @Test
    void testAddOrder_Duplicate3() {
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated_Duplicate3() {
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        orderService.addOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

    @Test
    void testAddOrder_Duplicate4() {
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated_Duplicate4() {
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        orderService.addOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

    @Test
    void testAddOrder_Duplicate5() {
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_OrderIdIsGenerated_Duplicate5() {
        Order newOrder = new Order();
        doNothing().when(orderRepository).addOrder(newOrder);

        orderService.addOrder(newOrder);

        assertNotNull(newOrder.getId());
    }

 }