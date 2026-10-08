package com.bv.platform.participation.application.queryservices;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.entities.AttendanceRecord;
import com.bv.platform.participation.domain.model.queries.GetActividadByIdQuery;
import com.bv.platform.participation.domain.model.queries.GetActividadByConvocatoriaIdQuery;
import com.bv.platform.participation.domain.model.queries.GetAttendanceListByActividadIdQuery;

import java.util.List;
import java.util.Optional;

public interface ParticipationQueryService {
    Optional<ActividadVoluntariado> handle(GetActividadByIdQuery query);
    Optional<ActividadVoluntariado> handle(GetActividadByConvocatoriaIdQuery query);
    List<AttendanceRecord> handle(GetAttendanceListByActividadIdQuery query);
}
