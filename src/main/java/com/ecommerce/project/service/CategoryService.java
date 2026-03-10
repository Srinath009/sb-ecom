package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category create(Category category);
    void delete(Long id);
    Category update(Long id, Category category);
}
