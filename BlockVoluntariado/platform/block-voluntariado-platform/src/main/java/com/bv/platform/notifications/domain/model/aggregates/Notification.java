package com.bv.platform.notifications.domain.model.aggregates;

import com.bv.platform.notifications.domain.model.valueobjects.NotificationType;
import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Notification extends AbstractDomainAggregateRoot<Notification> {

    @Setter
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime sentAt;

    public Notification() {
        this.isRead = false;
        this.sentAt = LocalDateTime.now();
        this.type = NotificationType.SISTEMA;
    }

    public Notification(Long id, Long userId, String title, String message,
                        NotificationType type, boolean isRead, LocalDateTime sentAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type != null ? type : NotificationType.SISTEMA;
        this.isRead = isRead;
        this.sentAt = sentAt != null ? sentAt : LocalDateTime.now();
    }

    public Notification(Long userId, String title, String message, NotificationType type) {
        this(null, userId, title, message, type, false, LocalDateTime.now());
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
