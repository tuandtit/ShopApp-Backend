package com.project.shopapp.controllers.impl;

import com.project.shopapp.controllers.CategoryController;
import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.CategoryListResponse;
import com.project.shopapp.dtos.responses.CategoryResponse;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@Validated
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;

    @Override
    public ApiResponse<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create category successfully")
                .result(categoryService.createCategory(categoryRequest))
                .build();
    }

    @Override
    public ApiResponse<CategoryListResponse> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ApiResponse.<CategoryListResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get categories with page: " + page + ", limit: " + limit)
                .result(categoryService.getCategories(page, limit))
                .build();
    }

    @Override
    public ResponseEntity<String> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok("update");
    }

    @Override
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete");
    }
}
