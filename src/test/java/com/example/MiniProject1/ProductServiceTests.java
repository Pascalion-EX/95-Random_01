package com.example.MiniProject1;


import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(productRepository.addProduct(product)).thenReturn(product);

        Product result = productService.addProduct(product);
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository, times(1)).addProduct(product);
    }
    @Test
    void testAddProduct_HappyPath() {
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(productRepository.addProduct(product)).thenReturn(product);

        Product result = productService.addProduct(product);
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository, times(1)).addProduct(product);
    }

    @Test
    void testGetProducts_HappyPath() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Test Product 1", 10.0));
        products.add(new Product(UUID.randomUUID(), "Test Product 2", 20.0));
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> result = productService.getProducts();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProductById_HappyPath() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product result = productService.getProductById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).getProductById(productId);
    }

    @Test
    void testUpdateProduct_HappyPath() {
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product(productId, "Updated Product", 20.0);
        when(productRepository.updateProduct(productId, "Updated Product", 20.0)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, "Updated Product", 20.0);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(20.0, result.getPrice());
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product", 20.0);
    }


    @Test
    void testAddProduct_MultipleProducts() {
        Product product1 = new Product(UUID.randomUUID(), "Test Product 1", 10.0);
        Product product2 = new Product(UUID.randomUUID(), "Test Product 2", 20.0);
        when(productRepository.addProduct(product1)).thenReturn(product1);
        when(productRepository.addProduct(product2)).thenReturn(product2);

        Product result1 = productService.addProduct(product1);
        Product result2 = productService.addProduct(product2);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(product1, result1);
        assertEquals(product2, result2);
        verify(productRepository, times(1)).addProduct(product1);
        verify(productRepository, times(1)).addProduct(product2);
    }

    @Test
    void testGetProductById_MultipleCalls() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product result1 = productService.getProductById(productId);
        Product result2 = productService.getProductById(productId);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(productId, result1.getId());
        assertEquals(productId, result2.getId());
        verify(productRepository, times(2)).getProductById(productId);
    }

    @Test
    void testUpdateProduct_MultipleUpdates() {
        UUID productId = UUID.randomUUID();
        Product updatedProduct1 = new Product(productId, "Updated Product 1", 20.0);
        Product updatedProduct2 = new Product(productId, "Updated Product 2", 30.0);
        when(productRepository.updateProduct(productId, "Updated Product 1", 20.0)).thenReturn(updatedProduct1);
        when(productRepository.updateProduct(productId, "Updated Product 2", 30.0)).thenReturn(updatedProduct2);

        Product result1 = productService.updateProduct(productId, "Updated Product 1", 20.0);
        Product result2 = productService.updateProduct(productId, "Updated Product 2", 30.0);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("Updated Product 1", result1.getName());
        assertEquals("Updated Product 2", result2.getName());
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product 1", 20.0);
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product 2", 30.0);
    }




    @Test
    void testGetProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Test Product", 10.0));
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> result = productService.getProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProducts_EmptyList() {
        when(productRepository.getProducts()).thenReturn(new ArrayList<>());

        ArrayList<Product> result = productService.getProducts();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProductById() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product result = productService.getProductById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).getProductById(productId);
    }
    @Test
    void testAddProduct_ValidInput() {
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.0);
        when(productRepository.addProduct(product)).thenReturn(product);

        Product result = productService.addProduct(product);
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository, times(1)).addProduct(product);
    }

    @Test
    void testGetProducts_NonEmptyList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Test Product 1", 10.0));
        products.add(new Product(UUID.randomUUID(), "Test Product 2", 20.0));
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> result = productService.getProducts();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProductById_ValidId() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product result = productService.getProductById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).getProductById(productId);
    }

    @Test
    void testUpdateProduct_ValidInput() {
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product(productId, "Updated Product", 20.0);
        when(productRepository.updateProduct(productId, "Updated Product", 20.0)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, "Updated Product", 20.0);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(20.0, result.getPrice());
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product", 20.0);
    }

    @Test
    void testAddProduct_MultipleValidProducts() {
        Product product1 = new Product(UUID.randomUUID(), "Test Product 1", 10.0);
        Product product2 = new Product(UUID.randomUUID(), "Test Product 2", 20.0);
        when(productRepository.addProduct(product1)).thenReturn(product1);
        when(productRepository.addProduct(product2)).thenReturn(product2);

        Product result1 = productService.addProduct(product1);
        Product result2 = productService.addProduct(product2);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(product1, result1);
        assertEquals(product2, result2);
        verify(productRepository, times(1)).addProduct(product1);
        verify(productRepository, times(1)).addProduct(product2);
    }

    @Test
    void testGetProductById_MultipleValidCalls() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 10.0);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product result1 = productService.getProductById(productId);
        Product result2 = productService.getProductById(productId);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(productId, result1.getId());
        assertEquals(productId, result2.getId());
        verify(productRepository, times(2)).getProductById(productId);
    }

    @Test
    void testUpdateProduct_MultipleValidUpdates() {
        UUID productId = UUID.randomUUID();
        Product updatedProduct1 = new Product(productId, "Updated Product 1", 20.0);
        Product updatedProduct2 = new Product(productId, "Updated Product 2", 30.0);
        when(productRepository.updateProduct(productId, "Updated Product 1", 20.0)).thenReturn(updatedProduct1);
        when(productRepository.updateProduct(productId, "Updated Product 2", 30.0)).thenReturn(updatedProduct2);

        Product result1 = productService.updateProduct(productId, "Updated Product 1", 20.0);
        Product result2 = productService.updateProduct(productId, "Updated Product 2", 30.0);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("Updated Product 1", result1.getName());
        assertEquals("Updated Product 2", result2.getName());
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product 1", 20.0);
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product 2", 30.0);
    }

    @Test
    void testAddProduct_WithMaxPrice() {
        Product product = new Product(UUID.randomUUID(), "Test Product", Double.MAX_VALUE);
        when(productRepository.addProduct(product)).thenReturn(product);

        Product result = productService.addProduct(product);
        assertNotNull(result);
        assertEquals(Double.MAX_VALUE, result.getPrice());
        verify(productRepository, times(1)).addProduct(product);
    }

    @Test
    void testUpdateProduct_WithMaxPrice() {
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product(productId, "Updated Product", Double.MAX_VALUE);
        when(productRepository.updateProduct(productId, "Updated Product", Double.MAX_VALUE)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, "Updated Product", Double.MAX_VALUE);
        assertNotNull(result);
        assertEquals(Double.MAX_VALUE, result.getPrice());
        verify(productRepository, times(1)).updateProduct(productId, "Updated Product", Double.MAX_VALUE);
    }

    @Test
    void testGetProducts_WithLargeList() {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            products.add(new Product(UUID.randomUUID(), "Test Product " + i, 10.0 + i));
        }
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> result = productService.getProducts();
        assertNotNull(result);
        assertEquals(1000, result.size());
        verify(productRepository, times(1)).getProducts();
    }


}
