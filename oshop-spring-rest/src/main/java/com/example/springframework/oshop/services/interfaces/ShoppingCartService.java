package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.domain.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart createShoppingCart(Product product);
    ShoppingCart addToShoppingCart(Product product, Long cartId);
    ShoppingCart findShoppingCartById(Long cartId);
    Boolean removeItemFromCart(Product product, Long cartId);
    Integer getShoppingCartIndicator(Long cartId);
    Boolean deleteShoppingCart(Long cartId);
}
