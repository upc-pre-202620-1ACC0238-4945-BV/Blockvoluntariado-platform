package com.bv.platform.recognition.domain.repositories;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;

import java.util.List;
import java.util.Optional;

public interface DigitalCertificateRepository {
    List<DigitalCertificate> findAll();
    Optional<DigitalCertificate> findById(Long id);
    Optional<DigitalCertificate> findByVerificationHash(String verificationHash);
    List<DigitalCertificate> findByVolunteerId(Long volunteerId);
    Optional<DigitalCertificate> findByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId);
    boolean existsByVolunteerIdAndConvocatoriaId(Long volunteerId, Long convocatoriaId);
    DigitalCertificate save(DigitalCertificate certificate);
}
