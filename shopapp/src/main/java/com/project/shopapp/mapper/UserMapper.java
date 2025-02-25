package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.UserRequest;
import com.project.shopapp.dtos.responses.UserResponse;
import com.project.shopapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = MapperConfig.class
)
public interface UserMapper extends EntityMapper<User, UserRequest, UserResponse>{
    @Override
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequest request);

    @Override
    @Mapping(target = "roles", ignore = true)
    void update(UserRequest dto, @MappingTarget User entity);
}
