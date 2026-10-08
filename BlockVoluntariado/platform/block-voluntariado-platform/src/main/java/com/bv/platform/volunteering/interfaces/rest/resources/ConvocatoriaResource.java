package com.bv.platform.volunteering.interfaces.rest.resources;

public record ConvocatoriaResource(
        Long id,
        Long organizationId,
        String title,
        String description,
        String causeType,
        int totalVacancies,
        int occupiedVacancies,
        int remainingVacancies,
        HorarioResource horario,
        UbicacionResource ubicacion,
        String status
) {}
