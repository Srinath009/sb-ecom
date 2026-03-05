package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<String> delete(Long  id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        existingCategory.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));

        categoryRepository.deleteById(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> update(Long id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        Category exist = existingCategory.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));

        category.setCategoryId(exist.getCategoryId());
        categoryRepository.save(category);
        return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
    }

}
