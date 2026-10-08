package com.bv.platform.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendNotificationResource(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId,

        @NotBlank(message = "El título es obligatorio")
        String title,

        @NotBlank(message = "El mensaje es obligatorio")
        String message,

        String type
) {}
