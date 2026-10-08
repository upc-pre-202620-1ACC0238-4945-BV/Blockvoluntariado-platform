package com.bv.platform.recognition.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record IssueCertificateResource(
        @NotNull(message = "El ID de la convocatoria es obligatorio")
        Long convocatoriaId,

        Integer accreditedHours
) {}
