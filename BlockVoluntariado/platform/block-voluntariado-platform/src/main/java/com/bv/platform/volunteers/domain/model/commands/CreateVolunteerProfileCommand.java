package com.bv.platform.volunteers.domain.model.commands;

public record CreateVolunteerProfileCommand(
        Long userId,
        String firstName,
        String lastName,
        String dniDocument,
        String universityName,
        String studentCode,
        String phoneNumber
) {
}
