package com.bv.platform.volunteers.domain.model.commands;

public record UpdateVolunteerProfileCommand(
        Long volunteerId,
        String firstName,
        String lastName,
        String universityName,
        String studentCode,
        String phoneNumber
) {
}
