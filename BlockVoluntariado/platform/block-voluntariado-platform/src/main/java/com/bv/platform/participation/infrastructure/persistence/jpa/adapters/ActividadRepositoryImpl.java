package com.bv.platform.participation.infrastructure.persistence.jpa.adapters;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.repositories.ActividadRepository;
import com.bv.platform.participation.infrastructure.persistence.jpa.assemblers.ParticipationPersistenceAssembler;
import com.bv.platform.participation.infrastructure.persistence.jpa.repositories.ActividadPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActividadRepositoryImpl implements ActividadRepository {

    private final ActividadPersistenceRepository persistenceRepository;

    public ActividadRepositoryImpl(ActividadPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<ActividadVoluntariado> findAll() {
        return persistenceRepository.findAll().stream()
                .map(ParticipationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<ActividadVoluntariado> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(ParticipationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<ActividadVoluntariado> findByConvocatoriaId(Long convocatoriaId) {
        return persistenceRepository.findByConvocatoriaId(convocatoriaId)
                .map(ParticipationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public ActividadVoluntariado save(ActividadVoluntariado actividad) {
        var entity = ParticipationPersistenceAssembler.toPersistenceFromDomain(actividad);
        var saved = persistenceRepository.save(entity);
        return ParticipationPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByConvocatoriaId(Long convocatoriaId) {
        return persistenceRepository.existsByConvocatoriaId(convocatoriaId);
    }
}
