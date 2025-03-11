package com.example.MiniProject1;



import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCart() {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.addCart(cart)).thenReturn(cart);

        Cart result = cartService.addCart(cart);
        assertNotNull(result);
        assertEquals(cart, result);
        verify(cartRepository, times(1)).addCart(cart);
    }


    @Test
    void testGetCartById() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        Cart result = cartService.getCartById(cartId);
        assertNotNull(result);
        assertEquals(cartId, result.getId());
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testGetCartById_NotFound() {
        UUID cartId = UUID.randomUUID();
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        Cart result = cartService.getCartById(cartId);
        assertNull(result);
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testGetCartByUserId() {
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        Cart result = cartService.getCartByUserId(userId);
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }

    @Test
    void testGetCartByUserId_NotFound() {
        UUID userId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(userId)).thenReturn(null);

        Cart result = cartService.getCartByUserId(userId);
        assertNull(result);
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }

    @Test
    void testAddProductToCart() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.addProductToCart(cartId, product);
        assertTrue(cart.getProducts().contains(product));
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddProductToCart_CartNotFound() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        cartService.addProductToCart(cartId, product);
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testDeleteProductFromCart() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>(List.of(product)));
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.deleteProductFromCart(cartId, product);
        assertFalse(cart.getProducts().contains(product));
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteProductFromCart_CartNotFound() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        cartService.deleteProductFromCart(cartId, product);
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testDeleteProductFromCart_ProductNotFound() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.deleteProductFromCart(cartId, product);
        assertFalse(cart.getProducts().contains(product));
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteCartById() {
        UUID cartId = UUID.randomUUID();
        doNothing().when(cartRepository).deleteCartById(cartId);

        cartService.deleteCartById(cartId);
        verify(cartRepository, times(1)).deleteCartById(cartId);
    }

    @Test
    void testDeleteCartById_NotFound() {
        UUID cartId = UUID.randomUUID();
        doThrow(new IllegalArgumentException("Cart not found")).when(cartRepository).deleteCartById(cartId);

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteCartById(cartId));
        verify(cartRepository, times(1)).deleteCartById(cartId);
    }
    @Test
    void testAddCart_Duplicate() {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.addCart(cart)).thenReturn(cart);

        Cart result = cartService.addCart(cart);
        assertNotNull(result);
        assertEquals(cart, result);
        verify(cartRepository, times(1)).addCart(cart);
    }

    @Test
    void testGetCartById_Duplicate() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        Cart result = cartService.getCartById(cartId);
        assertNotNull(result);
        assertEquals(cartId, result.getId());
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testGetCartById_NotFound_Duplicate() {
        UUID cartId = UUID.randomUUID();
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        Cart result = cartService.getCartById(cartId);
        assertNull(result);
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testGetCartByUserId_Duplicate() {
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        Cart result = cartService.getCartByUserId(userId);
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }

    @Test
    void testGetCartByUserId_NotFound_Duplicate() {
        UUID userId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(userId)).thenReturn(null);

        Cart result = cartService.getCartByUserId(userId);
        assertNull(result);
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }

    @Test
    void testAddProductToCart_Duplicate() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>());
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.addProductToCart(cartId, product);
        assertTrue(cart.getProducts().contains(product));
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddProductToCart_CartNotFound_Duplicate() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        cartService.addProductToCart(cartId, product);
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testDeleteProductFromCart_Duplicate() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        Cart cart = new Cart(cartId, UUID.randomUUID(), new ArrayList<>(List.of(product)));
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.deleteProductFromCart(cartId, product);
        assertFalse(cart.getProducts().contains(product));
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteProductFromCart_CartNotFound_Duplicate() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        cartService.deleteProductFromCart(cartId, product);
        verify(cartRepository, times(1)).getCartById(cartId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testDeleteCartById_Duplicate() {
        UUID cartId = UUID.randomUUID();
        doNothing().when(cartRepository).deleteCartById(cartId);

        cartService.deleteCartById(cartId);
        verify(cartRepository, times(1)).deleteCartById(cartId);
    }
    }