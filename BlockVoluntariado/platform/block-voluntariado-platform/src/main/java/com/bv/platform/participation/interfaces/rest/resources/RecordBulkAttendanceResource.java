package com.bv.platform.participation.interfaces.rest.resources;

import java.util.List;

public record RecordBulkAttendanceResource(
        List<RecordAttendanceResource> attendances
) {}
