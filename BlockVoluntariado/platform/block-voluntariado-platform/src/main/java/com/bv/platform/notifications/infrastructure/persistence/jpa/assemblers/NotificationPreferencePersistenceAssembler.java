package com.bv.platform.notifications.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPreferencePersistenceEntity;

public final class NotificationPreferencePersistenceAssembler {

    private NotificationPreferencePersistenceAssembler() {
    }

    public static NotificationPreference toDomainFromPersistence(NotificationPreferencePersistenceEntity entity) {
        if (entity == null) return null;
        return new NotificationPreference(
                entity.getId(),
                entity.getUserId(),
                entity.isEmailEnabled(),
                entity.isPushEnabled(),
                entity.isCauseAlerts()
        );
    }

    public static NotificationPreferencePersistenceEntity toPersistenceFromDomain(NotificationPreference domain) {
        if (domain == null) return null;
        var entity = new NotificationPreferencePersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setUserId(domain.getUserId());
        entity.setEmailEnabled(domain.isEmailEnabled());
        entity.setPushEnabled(domain.isPushEnabled());
        entity.setCauseAlerts(domain.isCauseAlerts());
        return entity;
    }
}
