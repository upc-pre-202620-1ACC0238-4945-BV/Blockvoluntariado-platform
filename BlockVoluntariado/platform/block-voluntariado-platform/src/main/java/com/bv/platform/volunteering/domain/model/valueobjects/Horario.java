package com.bv.platform.volunteering.domain.model.valueobjects;

import java.time.LocalDate;

public record Horario(
        LocalDate startDate,
        LocalDate endDate,
        String startTime,
        String endTime
) {
    public Horario {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");
        }
    }

    public static Horario of(LocalDate startDate, LocalDate endDate, String startTime, String endTime) {
        return new Horario(startDate, endDate, startTime, endTime);
    }
}
