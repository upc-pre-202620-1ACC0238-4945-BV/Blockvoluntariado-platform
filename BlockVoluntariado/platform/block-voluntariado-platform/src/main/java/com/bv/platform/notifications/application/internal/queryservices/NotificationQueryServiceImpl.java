package com.bv.platform.notifications.application.internal.queryservices;

import com.bv.platform.notifications.application.queryservices.NotificationQueryService;
import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.domain.model.queries.GetNotificationPreferencesQuery;
import com.bv.platform.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.bv.platform.notifications.domain.repositories.NotificationPreferenceRepository;
import com.bv.platform.notifications.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository,
                                        NotificationPreferenceRepository preferenceRepository) {
        this.notificationRepository = notificationRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public List<Notification> handle(GetUserNotificationsQuery query) {
        return notificationRepository.findByUserId(query.userId());
    }

    @Override
    public NotificationPreference handle(GetNotificationPreferencesQuery query) {
        return preferenceRepository.findByUserId(query.userId())
                .orElseGet(() -> new NotificationPreference(query.userId()));
    }
}
