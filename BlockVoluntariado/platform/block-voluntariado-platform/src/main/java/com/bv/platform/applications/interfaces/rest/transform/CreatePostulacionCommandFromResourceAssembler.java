package com.bv.platform.applications.interfaces.rest.transform;

import com.bv.platform.applications.domain.model.commands.CreatePostulacionCommand;
import com.bv.platform.applications.interfaces.rest.resources.CreatePostulacionResource;

public final class CreatePostulacionCommandFromResourceAssembler {

    private CreatePostulacionCommandFromResourceAssembler() {
    }

    public static CreatePostulacionCommand toCommandFromResource(Long convocatoriaId, Long resolvedVolunteerId, CreatePostulacionResource resource) {
        Long volunteerId = resource != null && resource.volunteerId() != null && resource.volunteerId() > 0
                ? resource.volunteerId()
                : resolvedVolunteerId;

        return new CreatePostulacionCommand(convocatoriaId, volunteerId);
    }
}
