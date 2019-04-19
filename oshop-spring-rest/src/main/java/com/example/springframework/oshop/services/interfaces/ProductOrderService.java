package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    ProductOrder saveOrder(ProductOrder productOrder, String email);
    List<ProductOrder> findAll();
    List<ProductOrder> findAllByLoggedUser(String email);
    ProductOrder findOrderById(String email, Long orderId);
}
