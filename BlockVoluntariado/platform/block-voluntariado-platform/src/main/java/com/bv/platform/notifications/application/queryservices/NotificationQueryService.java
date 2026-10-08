package com.bv.platform.notifications.application.queryservices;

import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.domain.model.queries.GetNotificationPreferencesQuery;
import com.bv.platform.notifications.domain.model.queries.GetUserNotificationsQuery;

import java.util.List;

public interface NotificationQueryService {
    List<Notification> handle(GetUserNotificationsQuery query);
    NotificationPreference handle(GetNotificationPreferencesQuery query);
}
