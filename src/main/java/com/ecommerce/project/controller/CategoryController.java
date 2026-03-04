package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        // Logic to retrieve all categories from the database
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/admin/createcategory")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.create(category);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/deletecategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try{ categoryService.delete(id);}
        catch(ResponseStatusException e){ return new ResponseEntity<>(e.getReason(), e.getStatusCode());}
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/updatecategory/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {categoryService.update(id,category);}
        catch (ResponseStatusException e) { return new ResponseEntity<>(e.getReason(), e.getStatusCode());}
        return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
    }
}
