package com.bv.platform.applications.domain.model.commands;

public record RejectPostulacionCommand(
        Long postulacionId,
        String reason
) {}
