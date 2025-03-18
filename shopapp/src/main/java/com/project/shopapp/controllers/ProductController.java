package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.ProductRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.ProductImageResponse;
import com.project.shopapp.dtos.responses.ProductListResponse;
import com.project.shopapp.dtos.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/products")
public interface ProductController {
    @GetMapping("")
    ApiResponse<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    );

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long productId);

    @PostMapping("")
    ApiResponse<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest
    );

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<List<ProductImageResponse>> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException;
}
