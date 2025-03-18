package com.project.shopapp.controllers.impl;

import com.project.shopapp.controllers.AuthenticationController;
import com.project.shopapp.dtos.AccountDto;
import com.project.shopapp.dtos.requests.SignInGoogleRequest;
import com.project.shopapp.dtos.requests.SignInRequest;
import com.project.shopapp.dtos.requests.SignUpRequest;
import com.project.shopapp.dtos.responses.ApiResponse;
import com.project.shopapp.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public ApiResponse<AccountDto> signUp(final SignUpRequest request) {
        return ApiResponse.<AccountDto>builder()
                .code(HttpStatus.OK.value())
                .message("Sign up successfully")
                .result(authenticationService.signUp(request))
                .build();
    }

    @Override
    public ApiResponse<AccountDto> signIn(final SignInRequest request, final HttpServletResponse response) {
        return ApiResponse.<AccountDto>builder()
                .code(HttpStatus.OK.value())
                .message("Sign in successfully")
                .result(authenticationService.signIn(request, response))
                .build();
    }

    @Override
    public ApiResponse<AccountDto> getAccessTokenByRefreshToken(final HttpServletRequest request) {
        return ApiResponse.<AccountDto>builder()
                .code(HttpStatus.OK.value())
                .message("Get access token successfully")
                .result(authenticationService.accessTokenByRefreshToken(request))
                .build();
    }

    @Override
    public ApiResponse<AccountDto> signInByGoogle(final SignInGoogleRequest request, final HttpServletResponse response) {
        return ApiResponse.<AccountDto>builder()
                .code(HttpStatus.OK.value())
                .message("Sign in by google successfully")
                .result(authenticationService.signInGoogle(request, response))
                .build();
    }
}
