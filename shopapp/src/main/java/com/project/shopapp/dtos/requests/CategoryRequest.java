package com.project.shopapp.dtos.requests;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(@NotEmpty(message = "Category name cannot be empty") String name) {
}
