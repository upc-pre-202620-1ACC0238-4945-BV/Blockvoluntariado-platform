package com.bv.platform.participation.interfaces.rest.transform;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.interfaces.rest.resources.ActividadResource;

import java.util.List;

public final class ActividadResourceFromEntityAssembler {

    private ActividadResourceFromEntityAssembler() {
    }

    public static ActividadResource toResourceFromEntity(ActividadVoluntariado entity) {
        if (entity == null) return null;

        var asistencias = entity.getAsistencias() != null
                ? entity.getAsistencias().stream()
                .map(AttendanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList()
                : List.<com.bv.platform.participation.interfaces.rest.resources.AttendanceRecordResource>of();

        return new ActividadResource(
                entity.getId(),
                entity.getConvocatoriaId(),
                entity.getTitulo(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getFechaActividad(),
                asistencias
        );
    }
}
