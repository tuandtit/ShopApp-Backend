package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.ProductRequest;
import com.project.shopapp.dtos.responses.ProductImageResponse;
import com.project.shopapp.dtos.responses.ProductResponse;
import com.project.shopapp.exceptions.AppException;
import com.project.shopapp.exceptions.ErrorCode;
import com.project.shopapp.mapper.ProductImageMapper;
import com.project.shopapp.mapper.ProductMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepo;
    CategoryRepository categoryRepo;
    ProductImageRepository productImageRepo;
    ProductMapper productMapper;
    ProductImageMapper productImageMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category existsCategory = existsCategory(productRequest.getCategoryId());

        Product newProduct = productMapper.toEntity(productRequest);
        newProduct.setCategory(existsCategory);
        return productMapper.toResponseDto(productRepo.save(newProduct));
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productMapper.toResponseDto(findProductById(id));
    }

    private Product findProductById(Long id) {
        return productRepo.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        //get danh sach theo trang(page) va gioi han(limit)
        return productRepo
                .findAll(pageRequest)
                .map(productMapper::toResponseDto);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductRequest productRequest
    ) {
        Product existingProduct = findProductById(id);
        if (existingProduct != null) {
            Category existsCategory = existsCategory(productRequest.getCategoryId());

            productMapper.update(productRequest, existingProduct);
            existingProduct.setCategory(existsCategory);

            return productMapper.toResponseDto(productRepo.save(existingProduct));
        }
        return null;
    }

    private Category existsCategory(Long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        optionalProduct.ifPresent(productRepo::delete);
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepo.existsByName(name);
    }

    @Override
    public List<ProductImageResponse> createProductImage(Long productId, List<MultipartFile> files) throws IOException {

        Product existingProduct = findProductById(productId);

        files = files == null ? new ArrayList<>() : files;

        if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT)
            throw new AppException(ErrorCode.MAXIMUM_IMAGES_PER_PRODUCT);
        List<ProductImageResponse> responseList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0)
                continue;
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new AppException(ErrorCode.PAYLOAD_TOO_LARGE);
            }

            String fileName = storeFile(file);
//                Luu file trong db
            ProductImage newProductImage = ProductImage.builder()
                    .product(existingProduct)
                    .imageUrl(fileName)
                    .build();

            responseList.add(productImageMapper.toResponseDto(productImageRepo.save(newProductImage)));
        }

        return responseList;
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null)
            throw new AppException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
//        Kiem tra va tao thu muc neu no khong ton tai
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
//        Duong dan day du den file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
//        Sao chep file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
