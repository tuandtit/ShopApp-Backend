package com.project.shopapp.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(
        prefix = "jwt"
)
public record RsaKeyProperties(long expired, RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
}
