package com.bv.platform.volunteering.interfaces.rest.transform;

import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.interfaces.rest.resources.ConvocatoriaResource;
import com.bv.platform.volunteering.interfaces.rest.resources.HorarioResource;
import com.bv.platform.volunteering.interfaces.rest.resources.UbicacionResource;

public final class ConvocatoriaResourceFromEntityAssembler {

    private ConvocatoriaResourceFromEntityAssembler() {
    }

    public static ConvocatoriaResource toResourceFromEntity(Convocatoria entity) {
        if (entity == null) return null;

        HorarioResource horarioResource = null;
        if (entity.getHorario() != null) {
            horarioResource = new HorarioResource(
                    entity.getHorario().startDate(),
                    entity.getHorario().endDate(),
                    entity.getHorario().startTime(),
                    entity.getHorario().endTime()
            );
        }

        UbicacionResource ubicacionResource = null;
        if (entity.getUbicacion() != null) {
            ubicacionResource = new UbicacionResource(
                    entity.getUbicacion().district(),
                    entity.getUbicacion().addressLine(),
                    entity.getUbicacion().latitude(),
                    entity.getUbicacion().longitude()
            );
        }

        return new ConvocatoriaResource(
                entity.getId(),
                entity.getOrganizationId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCauseType(),
                entity.getTotalVacancies(),
                entity.getOccupiedVacancies(),
                entity.getRemainingVacancies(),
                horarioResource,
                ubicacionResource,
                entity.getStatus() != null ? entity.getStatus().name() : null
        );
    }
}
