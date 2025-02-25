package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.PermissionRequest;
import com.project.shopapp.dtos.responses.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permissionName);
}
