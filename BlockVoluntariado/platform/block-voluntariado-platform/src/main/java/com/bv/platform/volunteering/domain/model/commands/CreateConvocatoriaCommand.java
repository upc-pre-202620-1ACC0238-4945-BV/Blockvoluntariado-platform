package com.bv.platform.volunteering.domain.model.commands;

import java.time.LocalDate;

public record CreateConvocatoriaCommand(
        Long organizationId,
        String title,
        String description,
        String causeType,
        int totalVacancies,
        LocalDate startDate,
        LocalDate endDate,
        String startTime,
        String endTime,
        String district,
        String addressLine,
        Double latitude,
        Double longitude
) {}
