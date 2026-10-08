package com.bv.platform.notifications.domain.repositories;

import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;

import java.util.Optional;

public interface NotificationPreferenceRepository {
    Optional<NotificationPreference> findByUserId(Long userId);
    NotificationPreference save(NotificationPreference preference);
}
