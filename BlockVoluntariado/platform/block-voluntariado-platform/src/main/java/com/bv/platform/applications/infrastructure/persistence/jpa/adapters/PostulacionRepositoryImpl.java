package com.bv.platform.applications.infrastructure.persistence.jpa.adapters;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.repositories.PostulacionRepository;
import com.bv.platform.applications.infrastructure.persistence.jpa.assemblers.PostulacionPersistenceAssembler;
import com.bv.platform.applications.infrastructure.persistence.jpa.repositories.PostulacionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostulacionRepositoryImpl implements PostulacionRepository {

    private final PostulacionPersistenceRepository persistenceRepository;

    public PostulacionRepositoryImpl(PostulacionPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<Postulacion> findAll() {
        return persistenceRepository.findAll().stream()
                .map(PostulacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Postulacion> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(PostulacionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Postulacion> findByConvocatoriaId(Long convocatoriaId) {
        return persistenceRepository.findByConvocatoriaId(convocatoriaId).stream()
                .map(PostulacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Postulacion> findByVolunteerId(Long volunteerId) {
        return persistenceRepository.findByVolunteerId(volunteerId).stream()
                .map(PostulacionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Postulacion> findByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId) {
        return persistenceRepository.findByConvocatoriaIdAndVolunteerId(convocatoriaId, volunteerId)
                .map(PostulacionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId) {
        return persistenceRepository.existsByConvocatoriaIdAndVolunteerId(convocatoriaId, volunteerId);
    }

    @Override
    public Postulacion save(Postulacion postulacion) {
        var entity = PostulacionPersistenceAssembler.toPersistenceFromDomain(postulacion);
        var saved = persistenceRepository.save(entity);
        return PostulacionPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}
