package com.bv.platform.notifications.domain.model.commands;

import com.bv.platform.notifications.domain.model.valueobjects.NotificationType;

public record SendNotificationCommand(
        Long userId,
        String title,
        String message,
        NotificationType type
) {}
