package com.bv.platform.notifications.interfaces.acl;

import com.bv.platform.notifications.application.commandservices.NotificationCommandService;
import com.bv.platform.notifications.domain.model.commands.SendNotificationCommand;
import com.bv.platform.notifications.domain.model.valueobjects.NotificationType;
import com.bv.platform.notifications.domain.repositories.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class NotificationsContextFacade {

    private final NotificationCommandService commandService;
    private final NotificationRepository notificationRepository;

    public NotificationsContextFacade(NotificationCommandService commandService,
                                      NotificationRepository notificationRepository) {
        this.commandService = commandService;
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(Long userId, String title, String message, NotificationType type) {
        if (userId != null && title != null && message != null) {
            commandService.handle(new SendNotificationCommand(userId, title, message, type != null ? type : NotificationType.SISTEMA));
        }
    }

    public int countUnreadNotifications(Long userId) {
        if (userId == null) return 0;
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
