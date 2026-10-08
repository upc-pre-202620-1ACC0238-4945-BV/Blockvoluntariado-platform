package com.bv.platform.participation.interfaces.acl;

import com.bv.platform.participation.application.queryservices.ParticipationQueryService;
import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.queries.GetActividadByConvocatoriaIdQuery;
import com.bv.platform.participation.domain.model.queries.GetActividadByIdQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParticipationContextFacade {

    private final ParticipationQueryService queryService;

    public ParticipationContextFacade(ParticipationQueryService queryService) {
        this.queryService = queryService;
    }

    public Optional<ActividadVoluntariado> fetchActividadById(Long actividadId) {
        if (actividadId == null) return Optional.empty();
        return queryService.handle(new GetActividadByIdQuery(actividadId));
    }

    public Optional<ActividadVoluntariado> fetchActividadByConvocatoriaId(Long convocatoriaId) {
        if (convocatoriaId == null) return Optional.empty();
        return queryService.handle(new GetActividadByConvocatoriaIdQuery(convocatoriaId));
    }

    public boolean hasCompletedParticipation(Long volunteerId, Long convocatoriaId) {
        if (volunteerId == null || convocatoriaId == null) return false;
        return fetchActividadByConvocatoriaId(convocatoriaId)
                .map(act -> act.isCompleted() && act.getAsistencias().stream()
                        .anyMatch(a -> a.getVolunteerId().equals(volunteerId) && a.isPresent()))
                .orElse(false);
    }

    public int getCertifiedHours(Long volunteerId, Long convocatoriaId) {
        if (volunteerId == null || convocatoriaId == null) return 0;
        return fetchActividadByConvocatoriaId(convocatoriaId)
                .flatMap(act -> act.getAsistencias().stream()
                        .filter(a -> a.getVolunteerId().equals(volunteerId) && a.isPresent())
                        .findFirst()
                        .map(a -> a.getCertifiedHours()))
                .orElse(0);
    }
}
