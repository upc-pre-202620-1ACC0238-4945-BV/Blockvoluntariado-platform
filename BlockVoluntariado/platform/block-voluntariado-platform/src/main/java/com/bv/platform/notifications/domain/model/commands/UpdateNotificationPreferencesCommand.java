package com.bv.platform.notifications.domain.model.commands;

public record UpdateNotificationPreferencesCommand(
        Long userId,
        boolean emailEnabled,
        boolean pushEnabled,
        boolean causeAlerts
) {}
