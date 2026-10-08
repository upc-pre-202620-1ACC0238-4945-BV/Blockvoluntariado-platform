package com.bv.platform.notifications.infrastructure.persistence.jpa.adapters;

import com.bv.platform.notifications.domain.model.aggregates.Notification;
import com.bv.platform.notifications.domain.repositories.NotificationRepository;
import com.bv.platform.notifications.infrastructure.persistence.jpa.assemblers.NotificationPersistenceAssembler;
import com.bv.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationPersistenceRepository persistenceRepository;

    public NotificationRepositoryImpl(NotificationPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return persistenceRepository.findByUserIdOrderBySentAtDesc(userId).stream()
                .map(NotificationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(NotificationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Notification save(Notification notification) {
        var entity = NotificationPersistenceAssembler.toPersistenceFromDomain(notification);
        var saved = persistenceRepository.save(entity);
        return NotificationPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public List<Notification> saveAll(List<Notification> notifications) {
        var entities = notifications.stream()
                .map(NotificationPersistenceAssembler::toPersistenceFromDomain)
                .toList();
        return persistenceRepository.saveAll(entities).stream()
                .map(NotificationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public int countByUserIdAndIsReadFalse(Long userId) {
        return persistenceRepository.countByUserIdAndIsReadFalse(userId);
    }
}
