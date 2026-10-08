package com.bv.platform.volunteering.interfaces.rest.resources;

import java.time.LocalDate;

public record HorarioResource(
        LocalDate startDate,
        LocalDate endDate,
        String startTime,
        String endTime
) {}
