package com.bv.platform.volunteers.interfaces.rest.transform;

import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.interfaces.rest.resources.VolunteerPreferencesResource;
import com.bv.platform.volunteers.interfaces.rest.resources.VolunteerProfileResource;

public final class VolunteerProfileResourceFromEntityAssembler {

    private VolunteerProfileResourceFromEntityAssembler() {
    }

    public static VolunteerProfileResource toResourceFromEntity(VolunteerProfile entity) {
        if (entity == null) return null;

        var preferencesResource = entity.getPreferences() != null
                ? new VolunteerPreferencesResource(
                entity.getPreferences().causes(),
                entity.getPreferences().availability(),
                entity.getPreferences().preferredModality())
                : new VolunteerPreferencesResource(java.util.List.of(), "Fines de semana", "Presencial");

        return new VolunteerProfileResource(
                entity.getId(),
                entity.getUserId(),
                entity.getName().firstName(),
                entity.getName().lastName(),
                entity.getName().getFullName(),
                entity.getDniDocument(),
                entity.getUniversityName(),
                entity.getStudentCode(),
                entity.getPhoneNumber(),
                entity.getAccumulatedHours(),
                preferencesResource
        );
    }
}
