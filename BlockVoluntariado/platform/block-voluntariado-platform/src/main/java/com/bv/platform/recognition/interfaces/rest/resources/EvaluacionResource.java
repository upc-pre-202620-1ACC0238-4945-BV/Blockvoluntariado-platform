package com.bv.platform.recognition.interfaces.rest.resources;

import java.time.LocalDateTime;

public record EvaluacionResource(
        Long id,
        Long evaluadorId,
        Long evaluadoId,
        String tipoEvaluador,
        int score,
        String feedback,
        LocalDateTime fecha
) {}
