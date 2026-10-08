package com.bv.platform.recognition.infrastructure.persistence.jpa.repositories;

import com.bv.platform.recognition.infrastructure.persistence.jpa.entities.DigitalCertificatePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DigitalCertificatePersistenceRepository extends JpaRepository<DigitalCertificatePersistenceEntity, Long> {

    Optional<DigitalCertificatePersistenceEntity> findByVerificationHash(String verificationHash);

    List<DigitalCertificatePersistenceEntity> findByVolunteerId(Long volunteerId);

    Optional<DigitalCertificatePersistenceEntity> findByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId);

    boolean existsByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId);
}
