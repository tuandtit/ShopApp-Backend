package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.CategoryListResponse;
import com.project.shopapp.dtos.responses.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
public interface CategoryController {
    @PostMapping("")
    ApiResponse<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest
    );

    @GetMapping("")
    ApiResponse<CategoryListResponse> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    );

    @PutMapping("/{id}")
    ResponseEntity<String> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryRequest categoryRequest
    );

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable("id") Long id);
}
