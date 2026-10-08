package com.bv.platform.recognition.interfaces.rest.resources;

import java.time.LocalDateTime;

public record VolunteerHistoryItemResource(
        Long convocatoriaId,
        String convocatoriaTitle,
        String organizationName,
        int accreditedHours,
        String verificationHash,
        String certificateUrl,
        LocalDateTime issuedAt
) {}
