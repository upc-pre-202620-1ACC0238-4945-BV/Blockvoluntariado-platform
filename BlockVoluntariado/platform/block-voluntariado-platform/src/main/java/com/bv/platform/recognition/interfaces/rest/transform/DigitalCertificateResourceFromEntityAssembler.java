package com.bv.platform.recognition.interfaces.rest.transform;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.interfaces.rest.resources.DigitalCertificateResource;

public final class DigitalCertificateResourceFromEntityAssembler {

    private DigitalCertificateResourceFromEntityAssembler() {
    }

    public static DigitalCertificateResource toResourceFromEntity(DigitalCertificate entity) {
        if (entity == null) return null;
        return new DigitalCertificateResource(
                entity.getId(),
                entity.getVolunteerId(),
                entity.getConvocatoriaId(),
                entity.getVerificationHash(),
                entity.getAccreditedHours(),
                entity.getPdfDownloadUrl(),
                entity.getIssuedAt()
        );
    }
}
