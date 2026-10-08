package com.bv.platform.notifications.domain.model.aggregates;

import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationPreference extends AbstractDomainAggregateRoot<NotificationPreference> {

    @Setter
    private Long id;
    private Long userId;
    private boolean emailEnabled;
    private boolean pushEnabled;
    private boolean causeAlerts;

    public NotificationPreference() {
        this.emailEnabled = true;
        this.pushEnabled = true;
        this.causeAlerts = true;
    }

    public NotificationPreference(Long id, Long userId, boolean emailEnabled, boolean pushEnabled, boolean causeAlerts) {
        this.id = id;
        this.userId = userId;
        this.emailEnabled = emailEnabled;
        this.pushEnabled = pushEnabled;
        this.causeAlerts = causeAlerts;
    }

    public NotificationPreference(Long userId) {
        this(null, userId, true, true, true);
    }

    public void updatePreferences(boolean emailEnabled, boolean pushEnabled, boolean causeAlerts) {
        this.emailEnabled = emailEnabled;
        this.pushEnabled = pushEnabled;
        this.causeAlerts = causeAlerts;
    }
}
