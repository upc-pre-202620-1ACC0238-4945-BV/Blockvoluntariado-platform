package com.bv.platform.volunteers.infrastructure.persistence.jpa.repositories;

import com.bv.platform.volunteers.infrastructure.persistence.jpa.entities.VolunteerProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerProfilePersistenceRepository extends JpaRepository<VolunteerProfilePersistenceEntity, Long> {
    Optional<VolunteerProfilePersistenceEntity> findByUserId(Long userId);
    boolean existsByDniDocument(String dniDocument);
    boolean existsByUserId(Long userId);
}
