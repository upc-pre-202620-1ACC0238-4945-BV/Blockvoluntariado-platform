package com.bv.platform.participation.infrastructure.persistence.jpa.repositories;

import com.bv.platform.participation.infrastructure.persistence.jpa.entities.ActividadPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadPersistenceRepository extends JpaRepository<ActividadPersistenceEntity, Long> {

    Optional<ActividadPersistenceEntity> findByConvocatoriaId(Long convocatoriaId);

    boolean existsByConvocatoriaId(Long convocatoriaId);
}
