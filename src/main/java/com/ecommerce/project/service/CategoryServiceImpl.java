package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<Category>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void create(Category category) {
        category.setId(nextId++);
        categories.add(category);
    }

    @Override
    public ResponseEntity<String> delete(Long  id) {
        Category category = categories.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));
        categories.remove(category);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> update(Long id, Category category) {
        Category existingCategory = categories.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));
        existingCategory.setName(category.getName());
        return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
    }

}
