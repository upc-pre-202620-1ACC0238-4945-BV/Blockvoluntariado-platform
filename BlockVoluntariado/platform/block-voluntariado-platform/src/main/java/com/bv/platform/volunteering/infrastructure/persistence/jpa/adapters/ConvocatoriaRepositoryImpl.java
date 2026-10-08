package com.bv.platform.volunteering.infrastructure.persistence.jpa.adapters;

import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;
import com.bv.platform.volunteering.domain.repositories.ConvocatoriaRepository;
import com.bv.platform.volunteering.infrastructure.persistence.jpa.assemblers.ConvocatoriaPersistenceAssembler;
import com.bv.platform.volunteering.infrastructure.persistence.jpa.repositories.ConvocatoriaPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConvocatoriaRepositoryImpl implements ConvocatoriaRepository {

    private final ConvocatoriaPersistenceRepository persistenceRepository;

    public ConvocatoriaRepositoryImpl(ConvocatoriaPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<Convocatoria> findAll() {
        return persistenceRepository.findAll().stream()
                .map(ConvocatoriaPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Convocatoria> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(ConvocatoriaPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Convocatoria> findByOrganizationId(Long organizationId) {
        return persistenceRepository.findByOrganizationId(organizationId).stream()
                .map(ConvocatoriaPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Convocatoria> findFiltered(String causeType, String district, EstadoConvocatoria status) {
        return persistenceRepository.findFiltered(causeType, district, status).stream()
                .map(ConvocatoriaPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Convocatoria save(Convocatoria convocatoria) {
        var entity = ConvocatoriaPersistenceAssembler.toPersistenceFromDomain(convocatoria);
        var saved = persistenceRepository.save(entity);
        return ConvocatoriaPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }
}
