package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.ProductRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.dtos.responses.ProductImageResponse;
import com.project.shopapp.dtos.responses.ProductListResponse;
import com.project.shopapp.dtos.responses.ProductResponse;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ApiResponse<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
//        lay tong so trang
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();

        ProductListResponse list = ProductListResponse.builder()
                .products(products)
                .totalPage(totalPage)
                .build();

        return ApiResponse.<ProductListResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get list product successfully")
                .result(list)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get product by id successfully")
                .result(productService.getProductById(productId))
                .build();
    }

    @PostMapping("")
    public ApiResponse<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest
    ) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create product successfully")
                .result(productService.createProduct(productRequest))
                .build();
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<ProductImageResponse>> uploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        return ApiResponse.<List<ProductImageResponse>>builder()
                .code(HttpStatus.CREATED.value())
                .message("Upload image successfully")
                .result(productService.createProductImage(productId, files))
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductRequest productRequest
    ) {
       return ApiResponse.<ProductResponse>builder()
               .code(HttpStatus.NO_CONTENT.value())
               .message("update product successfully")
               .result(productService.updateProduct(id, productRequest))
               .build();
    }

    //    @PostMapping("/generateFakeProducts")
//    private ResponseEntity<String> generateFakeProducts() {
//        Faker faker = new Faker();
//        for (int i = 0; i < 3_000; i++) {
//            String productName = faker.commerce().productName();
//            if (productService.existsByName(productName))
//                continue;
//            ProductDTO productDTO = ProductDTO.builder()
//                    .name(productName)
//                    .price((float) faker.number().numberBetween(10, 90_000_000))
//                    .thumbnail("")
//                    .description(faker.lorem().sentence())
//                    .categoryId((long) faker.number().numberBetween(2, 6))
//                    .build();
//                productService.createProduct(productDTO);
//        }
//        return ResponseEntity.ok("fake data completed");
//    }
}
