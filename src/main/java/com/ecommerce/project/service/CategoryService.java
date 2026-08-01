package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,  String sortBy, String sortDir);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO delete(Long id);
    CategoryDTO update(Long id, CategoryDTO categoryDTO);
}
