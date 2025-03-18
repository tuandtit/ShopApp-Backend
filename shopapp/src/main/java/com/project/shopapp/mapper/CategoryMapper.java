package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.CategoryRequest;
import com.project.shopapp.dtos.responses.CategoryResponse;
import com.project.shopapp.models.Category;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface CategoryMapper extends EntityMapper<Category, CategoryRequest, CategoryResponse> {
}
