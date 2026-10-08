package com.bv.platform.recognition.application.queryservices;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByHashQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByIdQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificatesByVolunteerIdQuery;

import java.util.List;
import java.util.Optional;

public interface CertificateQueryService {
    Optional<DigitalCertificate> handle(GetCertificateByIdQuery query);
    Optional<DigitalCertificate> handle(GetCertificateByHashQuery query);
    List<DigitalCertificate> handle(GetCertificatesByVolunteerIdQuery query);
}
