package com.bv.platform.volunteering.interfaces.rest.transform;

import com.bv.platform.volunteering.domain.model.commands.UpdateConvocatoriaCommand;
import com.bv.platform.volunteering.interfaces.rest.resources.UpdateConvocatoriaResource;

public final class UpdateConvocatoriaCommandFromResourceAssembler {

    private UpdateConvocatoriaCommandFromResourceAssembler() {
    }

    public static UpdateConvocatoriaCommand toCommandFromResource(Long convocatoriaId, UpdateConvocatoriaResource resource) {
        var h = resource.horario();
        var u = resource.ubicacion();

        return new UpdateConvocatoriaCommand(
                convocatoriaId,
                resource.title(),
                resource.description(),
                resource.causeType(),
                resource.totalVacancies() != null ? resource.totalVacancies() : 0,
                h != null ? h.startDate() : null,
                h != null ? h.endDate() : null,
                h != null ? h.startTime() : null,
                h != null ? h.endTime() : null,
                u != null ? u.district() : null,
                u != null ? u.addressLine() : null,
                u != null ? u.latitude() : null,
                u != null ? u.longitude() : null
        );
    }
}
