package com.bv.platform.volunteers.interfaces.rest.transform;

import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerProfileCommand;
import com.bv.platform.volunteers.interfaces.rest.resources.UpdateVolunteerProfileResource;

public final class UpdateVolunteerProfileCommandFromResourceAssembler {

    private UpdateVolunteerProfileCommandFromResourceAssembler() {
    }

    public static UpdateVolunteerProfileCommand toCommandFromResource(Long volunteerId, UpdateVolunteerProfileResource resource) {
        return new UpdateVolunteerProfileCommand(
                volunteerId,
                resource.firstName(),
                resource.lastName(),
                resource.universityName(),
                resource.studentCode(),
                resource.phoneNumber()
        );
    }
}
