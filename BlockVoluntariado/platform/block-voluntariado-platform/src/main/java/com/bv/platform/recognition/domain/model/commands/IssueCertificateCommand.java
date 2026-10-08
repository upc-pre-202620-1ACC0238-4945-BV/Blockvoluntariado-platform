package com.bv.platform.recognition.domain.model.commands;

public record IssueCertificateCommand(
        Long volunteerId,
        Long convocatoriaId,
        Integer accreditedHours
) {}
