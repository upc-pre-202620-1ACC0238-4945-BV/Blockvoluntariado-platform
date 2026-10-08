package com.bv.platform.participation.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AttendanceRecordResource(
        Long id,
        Long actividadId,
        Long postulacionId,
        Long volunteerId,
        boolean isPresent,
        int certifiedHours,
        LocalDateTime checkInTime,
        String supervisorNotes
) {}
