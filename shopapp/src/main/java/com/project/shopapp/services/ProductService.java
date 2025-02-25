package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.ProductRequest;
import com.project.shopapp.dtos.responses.ProductImageResponse;
import com.project.shopapp.dtos.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
    Boolean existsByName(String name);
    List<ProductImageResponse> createProductImage(Long productId, List<MultipartFile> files) throws IOException;
}
