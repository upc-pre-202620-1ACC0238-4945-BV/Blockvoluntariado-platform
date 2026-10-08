package com.bv.platform.volunteers.infrastructure.persistence.jpa.adapters;

import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.repositories.VolunteerProfileRepository;
import com.bv.platform.volunteers.infrastructure.persistence.jpa.assemblers.VolunteerProfilePersistenceAssembler;
import com.bv.platform.volunteers.infrastructure.persistence.jpa.repositories.VolunteerProfilePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VolunteerProfileRepositoryImpl implements VolunteerProfileRepository {

    private final VolunteerProfilePersistenceRepository persistenceRepository;

    public VolunteerProfileRepositoryImpl(VolunteerProfilePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<VolunteerProfile> findAll() {
        return persistenceRepository.findAll().stream()
                .map(VolunteerProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<VolunteerProfile> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(VolunteerProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<VolunteerProfile> findByUserId(Long userId) {
        return persistenceRepository.findByUserId(userId)
                .map(VolunteerProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByDniDocument(String dniDocument) {
        return persistenceRepository.existsByDniDocument(dniDocument);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return persistenceRepository.existsByUserId(userId);
    }

    @Override
    public VolunteerProfile save(VolunteerProfile volunteerProfile) {
        var entity = VolunteerProfilePersistenceAssembler.toPersistenceFromDomain(volunteerProfile);
        var saved = persistenceRepository.save(entity);
        return VolunteerProfilePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
