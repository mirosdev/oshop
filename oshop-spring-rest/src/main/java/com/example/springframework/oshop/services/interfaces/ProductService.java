package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    List<Product> findAll();
    Product findById(Long id);
    Boolean deleteById(Long id);
}
