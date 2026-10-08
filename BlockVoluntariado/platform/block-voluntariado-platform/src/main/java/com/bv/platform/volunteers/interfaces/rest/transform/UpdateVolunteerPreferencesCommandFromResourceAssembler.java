package com.bv.platform.volunteers.interfaces.rest.transform;

import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerPreferencesCommand;
import com.bv.platform.volunteers.interfaces.rest.resources.VolunteerPreferencesResource;

public final class UpdateVolunteerPreferencesCommandFromResourceAssembler {

    private UpdateVolunteerPreferencesCommandFromResourceAssembler() {
    }

    public static UpdateVolunteerPreferencesCommand toCommandFromResource(Long volunteerId, VolunteerPreferencesResource resource) {
        return new UpdateVolunteerPreferencesCommand(
                volunteerId,
                resource.causes(),
                resource.availability(),
                resource.preferredModality()
        );
    }
}
