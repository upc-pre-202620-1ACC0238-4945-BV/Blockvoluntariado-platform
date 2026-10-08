package com.bv.platform.volunteers.domain.model.commands;

import java.util.List;

public record UpdateVolunteerPreferencesCommand(
        Long volunteerId,
        List<String> causes,
        String availability,
        String preferredModality
) {
}
