package com.bv.platform.recognition.interfaces.acl;

import com.bv.platform.recognition.application.queryservices.CertificateQueryService;
import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByHashQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificatesByVolunteerIdQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecognitionContextFacade {

    private final CertificateQueryService certificateQueryService;

    public RecognitionContextFacade(CertificateQueryService certificateQueryService) {
        this.certificateQueryService = certificateQueryService;
    }

    public List<DigitalCertificate> fetchCertificatesByVolunteerId(Long volunteerId) {
        if (volunteerId == null) return List.of();
        return certificateQueryService.handle(new GetCertificatesByVolunteerIdQuery(volunteerId));
    }

    public Optional<DigitalCertificate> verifyCertificateByHash(String verificationHash) {
        if (verificationHash == null || verificationHash.isBlank()) return Optional.empty();
        return certificateQueryService.handle(new GetCertificateByHashQuery(verificationHash));
    }

    public int countCertificates(Long volunteerId) {
        return fetchCertificatesByVolunteerId(volunteerId).size();
    }
}
