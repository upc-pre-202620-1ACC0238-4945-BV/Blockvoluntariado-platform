package com.bv.platform.notifications.interfaces.rest.transform;

import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.interfaces.rest.resources.NotificationPreferencesResource;

public final class NotificationPreferencesResourceFromEntityAssembler {

    private NotificationPreferencesResourceFromEntityAssembler() {
    }

    public static NotificationPreferencesResource toResourceFromEntity(NotificationPreference entity) {
        if (entity == null) return null;
        return new NotificationPreferencesResource(
                entity.getUserId(),
                entity.isEmailEnabled(),
                entity.isPushEnabled(),
                entity.isCauseAlerts()
        );
    }
}
