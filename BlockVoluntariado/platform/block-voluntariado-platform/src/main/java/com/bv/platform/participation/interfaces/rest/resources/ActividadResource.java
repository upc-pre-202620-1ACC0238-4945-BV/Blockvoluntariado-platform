package com.bv.platform.participation.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record ActividadResource(
        Long id,
        Long convocatoriaId,
        String titulo,
        String status,
        LocalDate fechaActividad,
        List<AttendanceRecordResource> asistencias
) {}
