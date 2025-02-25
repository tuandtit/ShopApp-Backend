package com.project.shopapp.services;

import com.nimbusds.jose.JOSEException;
import com.project.shopapp.dtos.requests.AuthenticationRequest;
import com.project.shopapp.dtos.requests.IntrospectRequest;
import com.project.shopapp.dtos.requests.LogoutRequest;
import com.project.shopapp.dtos.requests.RefreshRequest;
import com.project.shopapp.dtos.responses.AuthenticationResponse;
import com.project.shopapp.dtos.responses.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
