package com.project.shopapp.mapper;

import com.project.shopapp.dtos.requests.PermissionRequest;
import com.project.shopapp.dtos.responses.PermissionResponse;
import com.project.shopapp.models.Permission;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class
)
public interface PermissionMapper extends EntityMapper<Permission, PermissionRequest, PermissionResponse> {
}
