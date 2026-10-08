package com.bv.platform.applications.interfaces.rest.resources;

import java.time.LocalDateTime;

public record PostulacionResource(
        Long id,
        Long convocatoriaId,
        Long volunteerId,
        String status,
        String rejectionReason,
        LocalDateTime appliedAt
) {}
