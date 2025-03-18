package com.project.shopapp.controllers;

import com.project.shopapp.dtos.AccountDto;
import com.project.shopapp.dtos.requests.SignInGoogleRequest;
import com.project.shopapp.dtos.requests.SignInRequest;
import com.project.shopapp.dtos.requests.SignUpRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface AuthenticationController {
    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<AccountDto> signUp(@Valid SignUpRequest request);

    @PostMapping("/sign-in")
    ApiResponse<AccountDto> signIn(@Valid @RequestBody SignInRequest request, final HttpServletResponse response);

    @PreAuthorize("hasAnyAuthority('SCOPE_REFRESH_TOKEN')")
    @PostMapping("/refresh-token")
    ApiResponse<AccountDto> getAccessTokenByRefreshToken(HttpServletRequest req);

    @PostMapping("/google/sign-in")
    ApiResponse<AccountDto> signInByGoogle(@Valid @RequestBody SignInGoogleRequest request, HttpServletResponse response);
}
