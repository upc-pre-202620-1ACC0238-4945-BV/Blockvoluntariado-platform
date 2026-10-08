package com.bv.platform.recognition.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.infrastructure.persistence.jpa.entities.DigitalCertificatePersistenceEntity;

public final class DigitalCertificatePersistenceAssembler {

    private DigitalCertificatePersistenceAssembler() {
    }

    public static DigitalCertificate toDomainFromPersistence(DigitalCertificatePersistenceEntity entity) {
        if (entity == null) return null;
        return new DigitalCertificate(
                entity.getId(),
                entity.getVolunteerId(),
                entity.getConvocatoriaId(),
                entity.getVerificationHash(),
                entity.getAccreditedHours(),
                entity.getPdfDownloadUrl(),
                entity.getIssuedAt()
        );
    }

    public static DigitalCertificatePersistenceEntity toPersistenceFromDomain(DigitalCertificate domain) {
        if (domain == null) return null;
        var entity = new DigitalCertificatePersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setVolunteerId(domain.getVolunteerId());
        entity.setConvocatoriaId(domain.getConvocatoriaId());
        entity.setVerificationHash(domain.getVerificationHash());
        entity.setAccreditedHours(domain.getAccreditedHours());
        entity.setPdfDownloadUrl(domain.getPdfDownloadUrl());
        entity.setIssuedAt(domain.getIssuedAt());
        return entity;
    }
}
