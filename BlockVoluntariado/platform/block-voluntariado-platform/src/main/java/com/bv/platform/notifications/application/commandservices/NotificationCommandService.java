package com.bv.platform.notifications.application.commandservices;

import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.domain.model.commands.MarkAllNotificationsAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.SendNotificationCommand;
import com.bv.platform.notifications.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;

import java.util.List;

public interface NotificationCommandService {
    Result<Notification, ApplicationError> handle(SendNotificationCommand command);
    Result<Notification, ApplicationError> handle(MarkNotificationAsReadCommand command);
    Result<List<Notification>, ApplicationError> handle(MarkAllNotificationsAsReadCommand command);
    Result<NotificationPreference, ApplicationError> handle(UpdateNotificationPreferencesCommand command);
}
