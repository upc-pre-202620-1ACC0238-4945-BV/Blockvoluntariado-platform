package com.bv.platform.notifications.interfaces.rest.resources;

public record NotificationPreferencesResource(
        Long userId,
        boolean emailEnabled,
        boolean pushEnabled,
        boolean causeAlerts
) {}
