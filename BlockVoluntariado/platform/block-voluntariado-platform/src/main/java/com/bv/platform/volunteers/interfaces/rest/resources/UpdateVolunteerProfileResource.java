package com.bv.platform.volunteers.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateVolunteerProfileResource(
        @NotBlank(message = "firstName is required")
        String firstName,

        @NotBlank(message = "lastName is required")
        String lastName,

        @NotBlank(message = "universityName is required")
        String universityName,

        @NotBlank(message = "studentCode is required")
        String studentCode,

        @NotBlank(message = "phoneNumber is required")
        String phoneNumber
) {
}
