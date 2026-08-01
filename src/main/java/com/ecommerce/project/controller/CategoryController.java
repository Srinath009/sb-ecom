package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                             @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                             @RequestParam(name="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                             @RequestParam(name="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDTO);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO deletedCategoryDTO = categoryService.delete(id);
        return ResponseEntity.ok(deletedCategoryDTO);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategoryDTO = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(updatedCategoryDTO);
    }
}
