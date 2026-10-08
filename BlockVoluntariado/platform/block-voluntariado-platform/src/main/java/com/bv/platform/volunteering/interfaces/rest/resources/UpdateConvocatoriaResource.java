package com.bv.platform.volunteering.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateConvocatoriaResource(
        @NotBlank(message = "El título es obligatorio")
        String title,

        String description,
        String causeType,

        @NotNull(message = "El total de vacantes es obligatorio")
        @Min(value = 1, message = "Debe haber al menos 1 vacante")
        Integer totalVacancies,

        HorarioResource horario,
        UbicacionResource ubicacion
) {}
