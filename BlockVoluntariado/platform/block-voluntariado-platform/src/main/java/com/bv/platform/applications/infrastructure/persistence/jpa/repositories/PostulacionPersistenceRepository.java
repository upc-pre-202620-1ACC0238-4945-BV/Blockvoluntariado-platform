package com.bv.platform.applications.infrastructure.persistence.jpa.repositories;

import com.bv.platform.applications.domain.model.valueobjects.EstadoPostulacion;
import com.bv.platform.applications.infrastructure.persistence.jpa.entities.PostulacionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulacionPersistenceRepository extends JpaRepository<PostulacionPersistenceEntity, Long> {

    List<PostulacionPersistenceEntity> findByConvocatoriaId(Long convocatoriaId);

    List<PostulacionPersistenceEntity> findByVolunteerId(Long volunteerId);

    Optional<PostulacionPersistenceEntity> findByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId);

    boolean existsByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId);

    List<PostulacionPersistenceEntity> findByConvocatoriaIdAndStatus(Long convocatoriaId, EstadoPostulacion status);
}
