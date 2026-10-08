package com.bv.platform.volunteers.interfaces.rest.resources;

import java.util.List;

public record VolunteerPreferencesResource(
        List<String> causes,
        String availability,
        String preferredModality
) {
}
