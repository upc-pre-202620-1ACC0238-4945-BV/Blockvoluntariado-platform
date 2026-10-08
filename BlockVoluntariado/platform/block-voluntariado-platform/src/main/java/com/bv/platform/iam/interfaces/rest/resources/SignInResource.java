package com.bv.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record SignInResource(
        @NotBlank(message = "Username or email is required")
        String username,
        @NotBlank(message = "Password is required")
        String password
) {
}
