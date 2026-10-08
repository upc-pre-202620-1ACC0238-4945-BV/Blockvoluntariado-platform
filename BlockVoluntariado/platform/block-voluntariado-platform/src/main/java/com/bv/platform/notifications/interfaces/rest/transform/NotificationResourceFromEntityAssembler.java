package com.bv.platform.notifications.interfaces.rest.transform;

import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.interfaces.rest.resources.NotificationResource;

public final class NotificationResourceFromEntityAssembler {

    private NotificationResourceFromEntityAssembler() {
    }

    public static NotificationResource toResourceFromEntity(Notification entity) {
        if (entity == null) return null;
        return new NotificationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType() != null ? entity.getType().name() : null,
                entity.isRead(),
                entity.getSentAt()
        );
    }
}
