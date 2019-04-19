package com.example.springframework.oshop.services.implementations;

import com.example.springframework.oshop.domain.Category;
import com.example.springframework.oshop.repositories.CategoriesRepository;
import com.example.springframework.oshop.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return categoriesRepository.saveAll(categories);
    }


}
