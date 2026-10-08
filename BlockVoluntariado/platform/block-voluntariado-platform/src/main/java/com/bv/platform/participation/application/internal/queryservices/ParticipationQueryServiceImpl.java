package com.bv.platform.participation.application.internal.queryservices;

import com.bv.platform.participation.application.queryservices.ParticipationQueryService;
import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.entities.AttendanceRecord;
import com.bv.platform.participation.domain.model.queries.GetActividadByIdQuery;
import com.bv.platform.participation.domain.model.queries.GetActividadByConvocatoriaIdQuery;
import com.bv.platform.participation.domain.model.queries.GetAttendanceListByActividadIdQuery;
import com.bv.platform.participation.domain.repositories.ActividadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationQueryServiceImpl implements ParticipationQueryService {

    private final ActividadRepository repository;

    public ParticipationQueryServiceImpl(ActividadRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ActividadVoluntariado> handle(GetActividadByIdQuery query) {
        return repository.findById(query.actividadId());
    }

    @Override
    public Optional<ActividadVoluntariado> handle(GetActividadByConvocatoriaIdQuery query) {
        return repository.findByConvocatoriaId(query.convocatoriaId());
    }

    @Override
    public List<AttendanceRecord> handle(GetAttendanceListByActividadIdQuery query) {
        return repository.findById(query.actividadId())
                .map(ActividadVoluntariado::getAsistencias)
                .orElse(List.of());
    }
}
