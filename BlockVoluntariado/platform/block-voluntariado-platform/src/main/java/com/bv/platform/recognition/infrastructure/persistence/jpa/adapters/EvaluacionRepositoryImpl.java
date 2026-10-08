package com.bv.platform.recognition.infrastructure.persistence.jpa.adapters;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.domain.repositories.EvaluacionRepository;
import com.bv.platform.recognition.infrastructure.persistence.jpa.assemblers.EvaluacionPersistenceAssembler;
import com.bv.platform.recognition.infrastructure.persistence.jpa.repositories.EvaluacionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EvaluacionRepositoryImpl implements EvaluacionRepository {

    private final EvaluacionPersistenceRepository persistenceRepository;

    public EvaluacionRepositoryImpl(EvaluacionPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<Evaluacion> findAll() {
        return persistenceRepository.findAll().stream()
                .map(EvaluacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Evaluacion> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(EvaluacionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Evaluacion> findByEvaluadoId(Long evaluadoId) {
        return persistenceRepository.findByEvaluadoId(evaluadoId).stream()
                .map(EvaluacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Evaluacion> findByEvaluadorId(Long evaluadorId) {
        return persistenceRepository.findByEvaluadorId(evaluadorId).stream()
                .map(EvaluacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        var entity = EvaluacionPersistenceAssembler.toPersistenceFromDomain(evaluacion);
        var saved = persistenceRepository.save(entity);
        return EvaluacionPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
