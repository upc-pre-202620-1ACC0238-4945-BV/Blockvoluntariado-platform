package com.bv.platform.volunteering.interfaces.rest.transform;

import com.bv.platform.volunteering.domain.model.commands.CreateConvocatoriaCommand;
import com.bv.platform.volunteering.interfaces.rest.resources.CreateConvocatoriaResource;

public final class CreateConvocatoriaCommandFromResourceAssembler {

    private CreateConvocatoriaCommandFromResourceAssembler() {
    }

    public static CreateConvocatoriaCommand toCommandFromResource(Long organizationId, CreateConvocatoriaResource resource) {
        var h = resource.horario();
        var u = resource.ubicacion();

        return new CreateConvocatoriaCommand(
                organizationId != null ? organizationId : resource.organizationId(),
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
