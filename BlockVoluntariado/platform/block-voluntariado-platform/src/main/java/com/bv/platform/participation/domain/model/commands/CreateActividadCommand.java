package com.bv.platform.participation.domain.model.commands;

import java.time.LocalDate;

public record CreateActividadCommand(
        Long convocatoriaId,
        String titulo,
        LocalDate fechaActividad
) {}
