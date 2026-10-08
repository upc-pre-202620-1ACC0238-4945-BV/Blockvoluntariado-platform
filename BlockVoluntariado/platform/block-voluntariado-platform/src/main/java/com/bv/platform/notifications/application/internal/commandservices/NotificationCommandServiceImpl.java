package com.bv.platform.notifications.application.internal.commandservices;

import com.bv.platform.notifications.application.commandservices.NotificationCommandService;
import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.domain.model.commands.MarkAllNotificationsAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.SendNotificationCommand;
import com.bv.platform.notifications.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.bv.platform.notifications.domain.repositories.NotificationPreferenceRepository;
import com.bv.platform.notifications.domain.repositories.NotificationRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository,
                                          NotificationPreferenceRepository preferenceRepository) {
        this.notificationRepository = notificationRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Result<Notification, ApplicationError> handle(SendNotificationCommand command) {
        if (command.userId() == null) {
            return Result.failure(ApplicationError.validationError("userId", "El ID del usuario destinatario es obligatorio."));
        }
        if (command.title() == null || command.title().isBlank()) {
            return Result.failure(ApplicationError.validationError("title", "El título de la notificación es obligatorio."));
        }
        if (command.message() == null || command.message().isBlank()) {
            return Result.failure(ApplicationError.validationError("message", "El mensaje de la notificación es obligatorio."));
        }

        var notification = new Notification(
                command.userId(),
                command.title(),
                command.message(),
                command.type()
        );
        var saved = notificationRepository.save(notification);
        return Result.success(saved);
    }

    @Override
    public Result<Notification, ApplicationError> handle(MarkNotificationAsReadCommand command) {
        var existing = notificationRepository.findById(command.notificationId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Notification", String.valueOf(command.notificationId())));
        }

        var notification = existing.get();
        notification.markAsRead();
        var saved = notificationRepository.save(notification);
        return Result.success(saved);
    }

    @Override
    public Result<List<Notification>, ApplicationError> handle(MarkAllNotificationsAsReadCommand command) {
        var userNotifications = notificationRepository.findByUserId(command.userId());
        for (var notif : userNotifications) {
            notif.markAsRead();
        }
        var updatedList = notificationRepository.saveAll(userNotifications);
        return Result.success(updatedList);
    }

    @Override
    public Result<NotificationPreference, ApplicationError> handle(UpdateNotificationPreferencesCommand command) {
        if (command.userId() == null) {
            return Result.failure(ApplicationError.validationError("userId", "El ID del usuario es obligatorio."));
        }

        var pref = preferenceRepository.findByUserId(command.userId())
                .orElseGet(() -> new NotificationPreference(command.userId()));

        pref.updatePreferences(command.emailEnabled(), command.pushEnabled(), command.causeAlerts());
        var saved = preferenceRepository.save(pref);
        return Result.success(saved);
    }
}
