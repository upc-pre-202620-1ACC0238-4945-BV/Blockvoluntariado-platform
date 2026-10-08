package com.bv.platform.notifications.infrastructure.persistence.jpa.adapters;

import com.bv.platform.notifications.domain.model.aggregates.NotificationPreference;
import com.bv.platform.notifications.domain.repositories.NotificationPreferenceRepository;
import com.bv.platform.notifications.infrastructure.persistence.jpa.assemblers.NotificationPreferencePersistenceAssembler;
import com.bv.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationPreferencePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NotificationPreferenceRepositoryImpl implements NotificationPreferenceRepository {

    private final NotificationPreferencePersistenceRepository persistenceRepository;

    public NotificationPreferenceRepositoryImpl(NotificationPreferencePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<NotificationPreference> findByUserId(Long userId) {
        return persistenceRepository.findByUserId(userId)
                .map(NotificationPreferencePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public NotificationPreference save(NotificationPreference preference) {
        var entity = NotificationPreferencePersistenceAssembler.toPersistenceFromDomain(preference);
        var saved = persistenceRepository.save(entity);
        return NotificationPreferencePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
