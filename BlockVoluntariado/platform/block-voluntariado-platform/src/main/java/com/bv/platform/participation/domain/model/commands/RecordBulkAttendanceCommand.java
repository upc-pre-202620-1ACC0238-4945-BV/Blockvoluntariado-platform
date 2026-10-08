package com.bv.platform.participation.domain.model.commands;

import java.util.List;

public record RecordBulkAttendanceCommand(
        Long actividadId,
        List<AttendanceItem> attendances
) {}
