package com.bv.platform.volunteering.interfaces.acl;

import com.bv.platform.volunteering.application.commandservices.ConvocatoriaCommandService;
import com.bv.platform.volunteering.application.queryservices.ConvocatoriaQueryService;
import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriaByIdQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VolunteeringContextFacade {

    private final ConvocatoriaCommandService commandService;
    private final ConvocatoriaQueryService queryService;

    public VolunteeringContextFacade(ConvocatoriaCommandService commandService,
                                     ConvocatoriaQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    public boolean existsById(Long convocatoriaId) {
        if (convocatoriaId == null) return false;
        return queryService.handle(new GetConvocatoriaByIdQuery(convocatoriaId)).isPresent();
    }

    public Optional<Convocatoria> fetchConvocatoriaById(Long convocatoriaId) {
        if (convocatoriaId == null) return Optional.empty();
        return queryService.handle(new GetConvocatoriaByIdQuery(convocatoriaId));
    }

    public boolean isConvocatoriaOpen(Long convocatoriaId) {
        return fetchConvocatoriaById(convocatoriaId)
                .map(Convocatoria::isOpen)
                .orElse(false);
    }

    public boolean hasAvailableVacancies(Long convocatoriaId) {
        return fetchConvocatoriaById(convocatoriaId)
                .map(Convocatoria::hasAvailableVacancies)
                .orElse(false);
    }

    public boolean incrementOccupiedVacancies(Long convocatoriaId) {
        if (convocatoriaId == null) return false;
        var result = commandService.incrementOccupiedVacancies(convocatoriaId);
        return result.isSuccess();
    }

    public boolean decrementOccupiedVacancies(Long convocatoriaId) {
        if (convocatoriaId == null) return false;
        var result = commandService.decrementOccupiedVacancies(convocatoriaId);
        return result.isSuccess();
    }

    public int getRemainingVacancies(Long convocatoriaId) {
        return fetchConvocatoriaById(convocatoriaId)
                .map(Convocatoria::getRemainingVacancies)
                .orElse(0);
    }

    public String getConvocatoriaTitle(Long convocatoriaId) {
        return fetchConvocatoriaById(convocatoriaId)
                .map(Convocatoria::getTitle)
                .orElse("Convocatoria #" + convocatoriaId);
    }

    public Long getConvocatoriaOrganizationId(Long convocatoriaId) {
        return fetchConvocatoriaById(convocatoriaId)
                .map(Convocatoria::getOrganizationId)
                .orElse(null);
    }
}
