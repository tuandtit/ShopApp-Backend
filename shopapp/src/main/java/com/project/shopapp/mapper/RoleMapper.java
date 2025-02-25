package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.RoleRequest;
import com.project.shopapp.dtos.responses.RoleResponse;
import com.project.shopapp.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = MapperConfig.class
)
public interface RoleMapper extends EntityMapper<Role, RoleRequest, RoleResponse> {
    @Override
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleRequest request);

    @Override
    @Mapping(target = "permissions", ignore = true)
    void update(RoleRequest dto,@MappingTarget Role entity);
}
