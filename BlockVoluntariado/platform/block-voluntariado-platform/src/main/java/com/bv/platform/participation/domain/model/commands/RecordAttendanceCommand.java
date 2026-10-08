package com.bv.platform.participation.domain.model.commands;

public record RecordAttendanceCommand(
        Long actividadId,
        Long postulacionId,
        Long volunteerId,
        boolean isPresent,
        int certifiedHours,
        String supervisorNotes
) {}
