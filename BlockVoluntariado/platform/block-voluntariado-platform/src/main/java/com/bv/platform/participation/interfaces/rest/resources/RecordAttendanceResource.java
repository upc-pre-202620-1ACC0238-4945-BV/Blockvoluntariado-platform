package com.bv.platform.participation.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record RecordAttendanceResource(
        Long postulacionId,

        @NotNull(message = "El ID del voluntario es obligatorio")
        Long volunteerId,

        Boolean isPresent,
        Integer certifiedHours,
        String supervisorNotes
) {}
