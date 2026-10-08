package com.bv.platform.volunteers.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateVolunteerProfileResource(
        @NotNull(message = "userId is required")
        Long userId,

        @NotBlank(message = "firstName is required")
        String firstName,

        @NotBlank(message = "lastName is required")
        String lastName,

        @NotBlank(message = "dniDocument is required")
        @Size(min = 8, max = 15, message = "DNI must have between 8 and 15 characters")
        String dniDocument,

        @NotBlank(message = "universityName is required")
        String universityName,

        @NotBlank(message = "studentCode is required")
        String studentCode,

        @NotBlank(message = "phoneNumber is required")
        String phoneNumber
) {
}
