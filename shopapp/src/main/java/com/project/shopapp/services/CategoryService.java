package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
