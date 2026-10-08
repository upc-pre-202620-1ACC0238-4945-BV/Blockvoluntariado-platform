package com.bv.platform.notifications.infrastructure.persistence.jpa.repositories;

import com.bv.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationPersistenceRepository extends JpaRepository<NotificationPersistenceEntity, Long> {

    List<NotificationPersistenceEntity> findByUserIdOrderBySentAtDesc(Long userId);

    int countByUserIdAndIsReadFalse(Long userId);
}
