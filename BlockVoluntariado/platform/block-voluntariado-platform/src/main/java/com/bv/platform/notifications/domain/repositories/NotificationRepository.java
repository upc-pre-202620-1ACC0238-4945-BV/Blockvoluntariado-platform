package com.bv.platform.notifications.domain.repositories;

import com.bv.platform.notifications.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    List<Notification> findByUserId(Long userId);
    Optional<Notification> findById(Long id);
    Notification save(Notification notification);
    List<Notification> saveAll(List<Notification> notifications);
    int countByUserIdAndIsReadFalse(Long userId);
}
