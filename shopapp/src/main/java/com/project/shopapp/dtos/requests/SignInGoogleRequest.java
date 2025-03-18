package com.project.shopapp.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInGoogleRequest {
    @NotBlank
    private String googleToken;

    @Email
    @NotBlank
    private String email;

    private String avatar;
}
