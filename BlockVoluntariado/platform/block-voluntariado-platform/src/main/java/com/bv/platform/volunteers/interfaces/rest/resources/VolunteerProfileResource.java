package com.bv.platform.volunteers.interfaces.rest.resources;

public record VolunteerProfileResource(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String fullName,
        String dniDocument,
        String universityName,
        String studentCode,
        String phoneNumber,
        int accumulatedHours,
        VolunteerPreferencesResource preferences
) {
}
