package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.UserRequest;
import com.project.shopapp.dtos.responses.UserResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse getMyInfo();

    @PostAuthorize("returnObject.username == authentication.name")
    UserResponse updateUser(Long userId, UserRequest request);

    void deleteUser(Long userId);

    @PreAuthorize("hasRole('ADMIN')")
    //    @PreAuthorize("hasAuthority('APPROVE_POST')")
    List<UserResponse> getUsers();

    @PreAuthorize("hasRole('ADMIN')")
    UserResponse getUser(Long id);
}
