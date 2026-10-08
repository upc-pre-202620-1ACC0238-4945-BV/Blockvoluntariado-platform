package com.bv.platform.participation.interfaces.rest.transform;

import com.bv.platform.participation.domain.model.commands.CreateActividadCommand;
import com.bv.platform.participation.interfaces.rest.resources.CreateActividadResource;

public final class CreateActividadCommandFromResourceAssembler {

    private CreateActividadCommandFromResourceAssembler() {
    }

    public static CreateActividadCommand toCommandFromResource(CreateActividadResource resource) {
        return new CreateActividadCommand(
                resource.convocatoriaId(),
                resource.titulo(),
                resource.fechaActividad()
        );
    }
}
