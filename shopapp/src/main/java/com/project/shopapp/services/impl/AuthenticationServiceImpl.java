package com.project.shopapp.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.project.shopapp.common.enums.AuthProvider;
import com.project.shopapp.common.enums.ERole;
import com.project.shopapp.dtos.AccountDto;
import com.project.shopapp.dtos.requests.SignInGoogleRequest;
import com.project.shopapp.dtos.requests.SignInRequest;
import com.project.shopapp.dtos.requests.SignUpRequest;
import com.project.shopapp.exceptions.BusinessException;
import com.project.shopapp.mapper.AccountMapper;
import com.project.shopapp.models.Account;
import com.project.shopapp.models.RefreshTokenEntity;
import com.project.shopapp.models.Role;
import com.project.shopapp.repositories.AccountRepository;
import com.project.shopapp.repositories.RefreshTokenRepository;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.security.jwt.TokenProvider;
import com.project.shopapp.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.project.shopapp.common.constant.AppConstant.PASSWORD_DEFAULT;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    //Other
    PasswordEncoder passwordEncoder;
    AuthenticationManagerBuilder authenticationManagerBuilder;
    TokenProvider tokenProvider;

    //Repository
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    RefreshTokenRepository refreshTokenRepository;

    //Mapper
    AccountMapper accountMapper;

    @Override
    public AccountDto signUp(SignUpRequest request) {
        if(accountRepository.existsByUsername(request.username())) {
            throw new BusinessException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Username is already in use");
        }
        final var entity = accountMapper.toEntity(request);
        Role role = roleRepository.findByName(ERole.USER).orElse(new Role(ERole.USER));
        entity.setPasswordHash(passwordEncoder.encode(request.password()));
        entity.addRole(role);
        return accountMapper.toDto(accountRepository.save(entity));
    }

    @Override
    public AccountDto signIn(SignInRequest request, HttpServletResponse response) {
        final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );

        final var authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwtAccessToken = tokenProvider.createToken(authentication);
        final var jwtRefreshToken = tokenProvider.createRefreshToken(authentication);

        final var username = tokenProvider.getUserName(jwtAccessToken);
        final var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("[SignInService:signIn] Account :{} not found", username);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCOUNT NOT FOUND");
                });

        updateRevokedRefreshToken(account);

        //Let's save the refreshToken as well
        saveRefreshToken(account, jwtRefreshToken);
        //Creating the cookie
        createRefreshTokenCookie(response, jwtRefreshToken);

        return accountMapper.toDto(jwtAccessToken, jwtRefreshToken);
    }

    @Override
    public AccountDto signInGoogle(SignInGoogleRequest request, HttpServletResponse response) {
        try {
            FirebaseAuth.getInstance().verifyIdToken(request.getGoogleToken());
            final var email = request.getEmail();
            final var account = accountRepository.findByUsernameAndAuthProvider(email, AuthProvider.GOOGLE)
                    .orElseGet(() -> createAccount(email, request.getAvatar()));

            final var authentication = tokenProvider.getAuthentication(account);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final var jwtAccessToken = tokenProvider.createToken(authentication);
            final var jwtRefreshToken = tokenProvider.createRefreshToken(authentication);

            updateRevokedRefreshToken(account);

            saveRefreshToken(account, jwtRefreshToken);

            createRefreshTokenCookie(response, jwtRefreshToken);

            return accountMapper.toDto(jwtAccessToken, jwtRefreshToken);

        } catch (FirebaseAuthException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }

    @Override
    public AccountDto accessTokenByRefreshToken(HttpServletRequest req) {
        final var refreshToken = tokenProvider.resolveToken(req);

        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please verify your token");
        }
        //Find refreshToken from database and should not be revoked : Same thing can be done through filter.
        var refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));

        final var account = refreshTokenEntity.getAccount();

        updateRevokedRefreshToken(account);

        //Now create the Authentication object
        final var authentication = tokenProvider.getAuthentication(account);

        //Use the authentication object to generate new jwt as the Authentication object that we will have may not contain correct role.
        final var jwtAccessToken = tokenProvider.createToken(authentication);

        return accountMapper.toDto(jwtAccessToken);
    }

    private Account createAccount(String username, String avatar) {
        final var account = new Account();
        account.setUsername(username);
        account.setAvatar(avatar);
        account.setPasswordHash(passwordEncoder.encode(PASSWORD_DEFAULT));
        account.setAuthProvider(AuthProvider.GOOGLE);
        account.setRoles(List.of(roleRepository.findByName(ERole.USER).orElse(new Role(ERole.USER))));
        return accountRepository.save(account);
    }

    private void updateRevokedRefreshToken(Account account) {
        final var refreshTokenEntities =refreshTokenRepository.findByAccountAndRevoked(account, false);
        refreshTokenEntities.forEach(refreshTokenEntity -> refreshTokenEntity.setRevoked(true));

        refreshTokenRepository.saveAll(refreshTokenEntities);
    }

    private void createRefreshTokenCookie(final HttpServletResponse servletResponse, final Jwt jwt) {
        final var refreshTokenCookie = new Cookie("refresh_token", jwt.getTokenValue());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 15 day to seconds
        servletResponse.addCookie(refreshTokenCookie);
    }

    private void saveRefreshToken(Account account, Jwt jwt) {
        final var refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setRefreshToken(jwt.getTokenValue());
        refreshTokenEntity.setAccount(account);
        refreshTokenRepository.save(refreshTokenEntity);
    }
}
