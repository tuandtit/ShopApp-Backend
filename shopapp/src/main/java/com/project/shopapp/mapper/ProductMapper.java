package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.ProductRequest;
import com.project.shopapp.dtos.responses.ProductResponse;
import com.project.shopapp.models.Product;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface ProductMapper extends EntityMapper<Product, ProductRequest, ProductResponse> {
}
