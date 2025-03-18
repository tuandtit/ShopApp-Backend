package com.project.shopapp.services;

import com.project.shopapp.dtos.AccountDto;
import com.project.shopapp.dtos.requests.SignInGoogleRequest;
import com.project.shopapp.dtos.requests.SignInRequest;
import com.project.shopapp.dtos.requests.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationService {
    AccountDto signUp(SignUpRequest request);

    AccountDto signIn(SignInRequest request, HttpServletResponse response);

    AccountDto accessTokenByRefreshToken(HttpServletRequest req);

    AccountDto signInGoogle(SignInGoogleRequest request, HttpServletResponse response);
}
