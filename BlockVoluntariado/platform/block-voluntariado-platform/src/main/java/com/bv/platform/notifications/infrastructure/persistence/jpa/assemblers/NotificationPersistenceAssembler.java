package com.bv.platform.notifications.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;

public final class NotificationPersistenceAssembler {

    private NotificationPersistenceAssembler() {
    }

    public static Notification toDomainFromPersistence(NotificationPersistenceEntity entity) {
        if (entity == null) return null;
        return new Notification(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType(),
                entity.isRead(),
                entity.getSentAt()
        );
    }

    public static NotificationPersistenceEntity toPersistenceFromDomain(Notification domain) {
        if (domain == null) return null;
        var entity = new NotificationPersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setUserId(domain.getUserId());
        entity.setTitle(domain.getTitle());
        entity.setMessage(domain.getMessage());
        entity.setType(domain.getType());
        entity.setRead(domain.isRead());
        entity.setSentAt(domain.getSentAt());
        return entity;
    }
}
