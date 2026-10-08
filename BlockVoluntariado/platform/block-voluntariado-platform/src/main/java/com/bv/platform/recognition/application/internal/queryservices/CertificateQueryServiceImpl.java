package com.bv.platform.recognition.application.internal.queryservices;

import com.bv.platform.recognition.application.queryservices.CertificateQueryService;
import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByHashQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByIdQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificatesByVolunteerIdQuery;
import com.bv.platform.recognition.domain.repositories.DigitalCertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateQueryServiceImpl implements CertificateQueryService {

    private final DigitalCertificateRepository repository;

    public CertificateQueryServiceImpl(DigitalCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DigitalCertificate> handle(GetCertificateByIdQuery query) {
        return repository.findById(query.certificateId());
    }

    @Override
    public Optional<DigitalCertificate> handle(GetCertificateByHashQuery query) {
        return repository.findByVerificationHash(query.verificationHash());
    }

    @Override
    public List<DigitalCertificate> handle(GetCertificatesByVolunteerIdQuery query) {
        return repository.findByVolunteerId(query.volunteerId());
    }
}
