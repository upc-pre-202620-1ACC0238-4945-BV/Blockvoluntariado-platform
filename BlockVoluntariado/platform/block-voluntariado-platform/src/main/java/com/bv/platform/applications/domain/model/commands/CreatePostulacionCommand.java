package com.bv.platform.applications.domain.model.commands;

public record CreatePostulacionCommand(
        Long convocatoriaId,
        Long volunteerId
) {}
