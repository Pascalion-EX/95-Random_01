package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import java.util.List;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {
    public CartRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/carts.json";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public Cart addCart(Cart cart) {
        ArrayList<Cart> carts = findAll();
        carts.add(cart);
        saveAll(carts);
        return cart;
    }

    public ArrayList<Cart> getCarts() {
        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        return findAll().stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .orElse(null);
    }
    public Cart getCartByUserId(UUID userId) {
        List<Cart> carts = findAll();
        // Iterate through the list to find the cart
        for (Cart cart : carts) {
            if (cart.getUserId().equals(userId)) {
                return cart; // Return the existing cart if found
            }
        }

        // If no cart is found, create a new one
        Cart newCart = new Cart(userId);
        carts.add(newCart);
        save(newCart);// Note: This change might not persist if `carts` is a temporary list

        return newCart;

    }


    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.getProducts().add(product);
                saveAll(carts);
                return;
            }
        }
    }

    public void deleteProductFromCart(UUID cartId, UUID productId) {
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.getProducts().removeIf(product -> product.getId().equals(productId));
                saveAll(carts);
                return;
            }
        }
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();
        boolean removed = carts.removeIf(cart -> cart.getId().equals(cartId));

        if (removed) {
            saveAll(carts);
        }
    }







}
