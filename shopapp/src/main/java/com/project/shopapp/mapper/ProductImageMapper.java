package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.ProductImageRequest;
import com.project.shopapp.dtos.responses.ProductImageResponse;
import com.project.shopapp.models.ProductImage;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface ProductImageMapper extends EntityMapper<ProductImage, ProductImageRequest, ProductImageResponse> {
}
