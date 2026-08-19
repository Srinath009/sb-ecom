package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Pageable pageable  = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), sortBy);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty())
            throw new APIException("No categories found");
        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category saved = categoryRepository.findByCategoryName(category.getCategoryName());
        if(saved != null){
            throw new APIException("Category with name " + category.getCategoryName() + " already exists");
        }
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO delete(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        categoryRepository.delete(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));

        Category category = modelMapper.map(categoryDTO, Category.class);

        // Check if new name conflicts with existing categories (excluding current one)
        Category categoryWithSameName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryWithSameName != null && !categoryWithSameName.getCategoryId().equals(id)) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists");
        }

        existingCategory.setCategoryName(category.getCategoryName());
        return modelMapper.map(categoryRepository.save(existingCategory), CategoryDTO.class);
    }
}
