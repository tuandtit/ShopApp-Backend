package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.RoleRequest;
import com.project.shopapp.dtos.responses.RoleResponse;
import com.project.shopapp.mapper.RoleMapper;
import com.project.shopapp.repositories.PermissionRepository;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toEntity(request);

        var permissions = permissionRepo.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepo.save(role);
        return roleMapper.toResponseDto(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepo.findAll().stream().map(roleMapper::toResponseDto).toList();
    }

    @Override
    public void delete(String role) {
        roleRepo.deleteById(role);
    }
}
