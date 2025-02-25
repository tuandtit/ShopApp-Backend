package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}
