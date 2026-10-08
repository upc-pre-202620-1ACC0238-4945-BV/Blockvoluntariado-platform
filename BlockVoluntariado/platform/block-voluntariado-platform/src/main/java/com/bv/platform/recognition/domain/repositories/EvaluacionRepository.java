package com.bv.platform.recognition.domain.repositories;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;

import java.util.List;
import java.util.Optional;

public interface EvaluacionRepository {
    List<Evaluacion> findAll();
    Optional<Evaluacion> findById(Long id);
    List<Evaluacion> findByEvaluadoId(Long evaluadoId);
    List<Evaluacion> findByEvaluadorId(Long evaluadorId);
    Evaluacion save(Evaluacion evaluacion);
}
