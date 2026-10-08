package com.bv.platform.recognition.interfaces.rest.resources;

import java.time.LocalDateTime;

public record DigitalCertificateResource(
        Long id,
        Long volunteerId,
        Long convocatoriaId,
        String verificationHash,
        int accreditedHours,
        String pdfDownloadUrl,
        LocalDateTime issuedAt
) {}
