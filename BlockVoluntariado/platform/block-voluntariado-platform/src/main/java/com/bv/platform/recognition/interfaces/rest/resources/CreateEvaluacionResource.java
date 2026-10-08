package com.bv.platform.recognition.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateEvaluacionResource(
        Long evaluadorId,

        @NotNull(message = "El puntaje es obligatorio")
        @Min(value = 1, message = "El puntaje mínimo es 1 estrella")
        @Max(value = 5, message = "El puntaje máximo es 5 estrellas")
        Integer score,

        String feedback
) {}
