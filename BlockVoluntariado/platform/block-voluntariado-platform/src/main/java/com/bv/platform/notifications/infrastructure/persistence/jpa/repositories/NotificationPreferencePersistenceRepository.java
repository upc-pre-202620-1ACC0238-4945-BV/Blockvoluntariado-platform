package com.bv.platform.notifications.infrastructure.persistence.jpa.repositories;

import com.bv.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPreferencePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationPreferencePersistenceRepository extends JpaRepository<NotificationPreferencePersistenceEntity, Long> {

    Optional<NotificationPreferencePersistenceEntity> findByUserId(Long userId);
}
