package com.bv.platform.volunteers.domain.model.valueobjects;

import java.util.List;

public record VolunteerPreferences(
        List<String> causes,
        String availability,
        String preferredModality
) {
    public static VolunteerPreferences empty() {
        return new VolunteerPreferences(List.of("Educación", "Medio Ambiente"), "Fines de semana", "Presencial");
    }
}
