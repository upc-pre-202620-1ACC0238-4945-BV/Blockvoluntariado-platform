package com.bv.platform.notifications.interfaces.rest.resources;

import java.time.LocalDateTime;

public record NotificationResource(
        Long id,
        Long userId,
        String title,
        String message,
        String type,
        boolean isRead,
        LocalDateTime sentAt
) {}
