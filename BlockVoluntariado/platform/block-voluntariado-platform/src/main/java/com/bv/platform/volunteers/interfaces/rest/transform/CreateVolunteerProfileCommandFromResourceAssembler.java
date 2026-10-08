package com.bv.platform.volunteers.interfaces.rest.transform;

import com.bv.platform.volunteers.domain.model.commands.CreateVolunteerProfileCommand;
import com.bv.platform.volunteers.interfaces.rest.resources.CreateVolunteerProfileResource;

public final class CreateVolunteerProfileCommandFromResourceAssembler {

    private CreateVolunteerProfileCommandFromResourceAssembler() {
    }

    public static CreateVolunteerProfileCommand toCommandFromResource(CreateVolunteerProfileResource resource) {
        return new CreateVolunteerProfileCommand(
                resource.userId(),
                resource.firstName(),
                resource.lastName(),
                resource.dniDocument(),
                resource.universityName(),
                resource.studentCode(),
                resource.phoneNumber()
        );
    }
}
