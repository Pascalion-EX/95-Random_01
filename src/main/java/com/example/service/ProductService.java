package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class ProductService extends MainService<Product> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    protected UUID getId(Product product) {
        return product.getId();
    }

    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID());
        productRepository.addProduct(product);
        return product;
    }

    public ArrayList<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Product getProductById(UUID productId) {
        return productRepository.getProductById(productId);
    }

    public Product updateProduct(UUID productId, String newName, double newPrice) {
        Product updatedProduct = productRepository.updateProduct(productId, newName, newPrice);
        if (updatedProduct == null) {
            throw new NoSuchElementException("Product with ID " + productId + " not found.");
        }
        return updatedProduct;
    }

    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }

        productRepository.applyDiscount(discount, productIds);
    }

    public void deleteProductById(UUID productId) {
        productRepository.deleteProductById(productId);
    }



}
