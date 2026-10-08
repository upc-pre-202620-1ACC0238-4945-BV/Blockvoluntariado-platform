package com.bv.platform.participation.interfaces.rest.transform;

import com.bv.platform.participation.domain.model.entities.AttendanceRecord;
import com.bv.platform.participation.interfaces.rest.resources.AttendanceRecordResource;

public final class AttendanceRecordResourceFromEntityAssembler {

    private AttendanceRecordResourceFromEntityAssembler() {
    }

    public static AttendanceRecordResource toResourceFromEntity(AttendanceRecord entity) {
        if (entity == null) return null;
        return new AttendanceRecordResource(
                entity.getId(),
                entity.getActividadId(),
                entity.getPostulacionId(),
                entity.getVolunteerId(),
                entity.isPresent(),
                entity.getCertifiedHours(),
                entity.getCheckInTime(),
                entity.getSupervisorNotes()
        );
    }
}
