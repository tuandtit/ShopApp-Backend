package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.dtos.responses.CategoryListResponse;
import com.project.shopapp.dtos.responses.CategoryResponse;
import com.project.shopapp.exceptions.AppException;
import com.project.shopapp.exceptions.ErrorCode;
import com.project.shopapp.mapper.CategoryMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    //Repository
    CategoryRepository categoryRepo;

    //Mapper
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .name(categoryRequest.name())
                .build();
        return categoryMapper.toDto(categoryRepo.save(newCategory));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toDto(existsCategoryById(id));
    }

    private Category existsCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryListResponse getCategories(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("id").descending());

        Page<CategoryResponse> categoryPage = categoryRepo
                .findAll(pageRequest)
                .map(categoryMapper::toDto);

        int totalPage = categoryPage.getTotalPages();
        List<CategoryResponse> categories = categoryPage.getContent();

        return CategoryListResponse.builder()
                .categories(categories)
                .totalPage(totalPage)
                .build();
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category existingCategory = existsCategoryById(categoryId);
        categoryMapper.update(categoryRequest, existingCategory);
        return categoryMapper.toDto(categoryRepo.save(existingCategory));
    }

    @Override
    public Boolean deleteCategory(Long id) {
        if (id != null) {
            existsCategoryById(id).setActive(false);
            return true;
        }
        return false;
    }
}
