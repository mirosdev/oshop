package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    List<Category> saveAll(List<Category> categories);
}
