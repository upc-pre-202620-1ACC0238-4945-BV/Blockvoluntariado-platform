package com.bv.platform.recognition.infrastructure.persistence.jpa.adapters;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.repositories.DigitalCertificateRepository;
import com.bv.platform.recognition.infrastructure.persistence.jpa.assemblers.DigitalCertificatePersistenceAssembler;
import com.bv.platform.recognition.infrastructure.persistence.jpa.repositories.DigitalCertificatePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DigitalCertificateRepositoryImpl implements DigitalCertificateRepository {

    private final DigitalCertificatePersistenceRepository persistenceRepository;

    public DigitalCertificateRepositoryImpl(DigitalCertificatePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public List<DigitalCertificate> findAll() {
        return persistenceRepository.findAll().stream()
                .map(DigitalCertificatePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<DigitalCertificate> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(DigitalCertificatePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<DigitalCertificate> findByVerificationHash(String verificationHash) {
        return persistenceRepository.findByVerificationHash(verificationHash)
                .map(DigitalCertificatePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<DigitalCertificate> findByVolunteerId(Long volunteerId) {
        return persistenceRepository.findByVolunteerId(volunteerId).stream()
                .map(DigitalCertificatePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<DigitalCertificate> findByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId) {
        return persistenceRepository.findByVolunteerIdAndConvocatoriaId(volunteerId, convocatoriaId)
                .map(DigitalCertificatePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId) {
        return persistenceRepository.existsByVolunteerIdAndConvocatoriaId(volunteerId, convocatoriaId);
    }

    @Override
    public DigitalCertificate save(DigitalCertificate certificate) {
        var entity = DigitalCertificatePersistenceAssembler.toPersistenceFromDomain(certificate);
        var saved = persistenceRepository.save(entity);
        return DigitalCertificatePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
