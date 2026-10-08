package com.bv.platform.applications.interfaces.acl;

import com.bv.platform.applications.application.queryservices.PostulacionQueryService;
import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.model.queries.GetPostulacionByIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByConvocatoriaIdQuery;
import com.bv.platform.applications.domain.model.valueobjects.EstadoPostulacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ApplicationsContextFacade {

    private final PostulacionQueryService queryService;

    public ApplicationsContextFacade(PostulacionQueryService queryService) {
        this.queryService = queryService;
    }

    public Optional<Postulacion> fetchPostulacionById(Long postulacionId) {
        if (postulacionId == null) return Optional.empty();
        return queryService.handle(new GetPostulacionByIdQuery(postulacionId));
    }

    public List<Long> fetchAcceptedVolunteerIdsByConvocatoriaId(Long convocatoriaId) {
        if (convocatoriaId == null) return List.of();
        return queryService.handle(new GetPostulacionesByConvocatoriaIdQuery(convocatoriaId)).stream()
                .filter(p -> p.getStatus() == EstadoPostulacion.ACEPTADA)
                .map(Postulacion::getVolunteerId)
                .toList();
    }

    public List<Postulacion> fetchAcceptedPostulacionesByConvocatoriaId(Long convocatoriaId) {
        if (convocatoriaId == null) return List.of();
        return queryService.handle(new GetPostulacionesByConvocatoriaIdQuery(convocatoriaId)).stream()
                .filter(p -> p.getStatus() == EstadoPostulacion.ACEPTADA)
                .toList();
    }

    public boolean isVolunteerAccepted(Long convocatoriaId, Long volunteerId) {
        if (convocatoriaId == null || volunteerId == null) return false;
        return queryService.handle(new GetPostulacionesByConvocatoriaIdQuery(convocatoriaId)).stream()
                .anyMatch(p -> p.getVolunteerId().equals(volunteerId) && p.getStatus() == EstadoPostulacion.ACEPTADA);
    }
}
