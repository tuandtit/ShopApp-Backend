package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.dtos.responses.CategoryListResponse;
import com.project.shopapp.dtos.responses.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    CategoryListResponse getCategories(int page, int limit);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    Boolean deleteCategory(Long id);
}
