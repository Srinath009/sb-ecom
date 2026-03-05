package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void create(Category category);
    ResponseEntity<String> delete(Long id);
    ResponseEntity<String> update(Long id, Category category);
}
