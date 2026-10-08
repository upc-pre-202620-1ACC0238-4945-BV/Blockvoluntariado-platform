package com.bv.platform.recognition.infrastructure.persistence.jpa.repositories;

import com.bv.platform.recognition.infrastructure.persistence.jpa.entities.EvaluacionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionPersistenceRepository extends JpaRepository<EvaluacionPersistenceEntity, Long> {

    List<EvaluacionPersistenceEntity> findByEvaluadoId(Long evaluadoId);

    List<EvaluacionPersistenceEntity> findByEvaluadorId(Long evaluadorId);
}
