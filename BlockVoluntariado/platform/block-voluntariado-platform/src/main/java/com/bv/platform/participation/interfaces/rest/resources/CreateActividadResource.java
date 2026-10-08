package com.bv.platform.participation.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateActividadResource(
        @NotNull(message = "El ID de la convocatoria es obligatorio")
        Long convocatoriaId,

        String titulo,
        LocalDate fechaActividad
) {}
