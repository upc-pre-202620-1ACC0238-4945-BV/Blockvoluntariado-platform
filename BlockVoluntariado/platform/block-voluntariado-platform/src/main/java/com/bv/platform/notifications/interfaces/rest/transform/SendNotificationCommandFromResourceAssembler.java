package com.bv.platform.notifications.interfaces.rest.transform;

import com.bv.platform.notifications.domain.model.commands.SendNotificationCommand;
import com.bv.platform.notifications.domain.model.valueobjects.NotificationType;
import com.bv.platform.notifications.interfaces.rest.resources.SendNotificationResource;

public final class SendNotificationCommandFromResourceAssembler {

    private SendNotificationCommandFromResourceAssembler() {
    }

    public static SendNotificationCommand toCommandFromResource(SendNotificationResource resource) {
        NotificationType type = NotificationType.SISTEMA;
        if (resource.type() != null && !resource.type().isBlank()) {
            try {
                type = NotificationType.valueOf(resource.type().toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
        }

        return new SendNotificationCommand(
                resource.userId(),
                resource.title(),
                resource.message(),
                type
        );
    }
}
