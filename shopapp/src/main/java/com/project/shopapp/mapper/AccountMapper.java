package com.project.shopapp.mapper;

import com.project.shopapp.common.enums.TokenType;
import com.project.shopapp.dtos.AccountDto;
import com.project.shopapp.dtos.requests.SignUpRequest;
import com.project.shopapp.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.jwt.Jwt;

@Mapper(
        config = MapperConfig.class,
        imports = {TokenType.class}
)
public interface AccountMapper extends EntityMapper<Account, AccountDto, AccountDto> {
    @Mapping(target = "avatar", ignore = true)
    Account toEntity(SignUpRequest request);

    @Mapping(target = "username", source = "token.subject")
    @Mapping(target = "tokenType", expression = "java(TokenType.Bearer.name())")
    @Mapping(target = "token", source = "token.tokenValue")
    @Mapping(target = "refreshToken", source = "refreshToken.tokenValue")
    @Mapping(target = "tokenExpiry", source = "token.expiresAt")
    @Mapping(target = "refreshTokenExpiry", source = "refreshToken.expiresAt")
    AccountDto toDto(Jwt token, Jwt refreshToken);

    @Mapping(target = "username", source = "token.subject")
    @Mapping(target = "tokenType", expression = "java(TokenType.Bearer.name())")
    @Mapping(target = "token", source = "token.tokenValue")
    @Mapping(target = "tokenExpiry", source = "token.expiresAt")
    AccountDto toDto(Jwt token);
}
