package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.RoleRequest;
import com.project.shopapp.dtos.responses.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String role);
}
