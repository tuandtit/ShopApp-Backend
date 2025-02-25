package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.PermissionRequest;
import com.project.shopapp.dtos.responses.PermissionResponse;
import com.project.shopapp.mapper.PermissionMapper;
import com.project.shopapp.models.Permission;
import com.project.shopapp.repositories.PermissionRepository;
import com.project.shopapp.services.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepo;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toEntity(request);
        permission = permissionRepo.save(permission);

        return permissionMapper.toResponseDto(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepo.findAll();

        return permissions.stream().map(permissionMapper::toResponseDto).toList();
    }

    @Override
    public void delete(String permissionName) {
        permissionRepo.deleteById(permissionName);
    }
}
