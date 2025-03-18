package com.project.shopapp.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record GetAccessTokenByRefreshTokenRequest(@NotBlank String refreshToken) {
}
