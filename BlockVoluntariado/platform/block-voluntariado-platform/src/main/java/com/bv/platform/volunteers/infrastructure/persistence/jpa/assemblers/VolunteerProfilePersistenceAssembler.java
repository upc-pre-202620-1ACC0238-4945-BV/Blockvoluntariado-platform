package com.bv.platform.volunteers.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.model.valueobjects.PersonalName;
import com.bv.platform.volunteers.domain.model.valueobjects.VolunteerPreferences;
import com.bv.platform.volunteers.infrastructure.persistence.jpa.entities.VolunteerProfilePersistenceEntity;

import java.util.Arrays;
import java.util.List;

public final class VolunteerProfilePersistenceAssembler {

    private VolunteerProfilePersistenceAssembler() {
    }

    public static VolunteerProfile toDomainFromPersistence(VolunteerProfilePersistenceEntity entity) {
        if (entity == null) return null;

        List<String> causesList = entity.getCauses() != null && !entity.getCauses().isBlank()
                ? Arrays.stream(entity.getCauses().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList()
                : List.of();

        var preferences = new VolunteerPreferences(
                causesList,
                entity.getAvailability() != null ? entity.getAvailability() : "Fines de semana",
                entity.getPreferredModality() != null ? entity.getPreferredModality() : "Presencial"
        );

        return new VolunteerProfile(
                entity.getId(),
                entity.getUserId(),
                new PersonalName(entity.getFirstName(), entity.getLastName()),
                entity.getDniDocument(),
                entity.getUniversityName(),
                entity.getStudentCode(),
                entity.getPhoneNumber(),
                entity.getAccumulatedHours(),
                preferences
        );
    }

    public static VolunteerProfilePersistenceEntity toPersistenceFromDomain(VolunteerProfile domain) {
        if (domain == null) return null;
        var entity = new VolunteerProfilePersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setUserId(domain.getUserId());
        entity.setFirstName(domain.getName().firstName());
        entity.setLastName(domain.getName().lastName());
        entity.setDniDocument(domain.getDniDocument());
        entity.setUniversityName(domain.getUniversityName());
        entity.setStudentCode(domain.getStudentCode());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setAccumulatedHours(domain.getAccumulatedHours());

        if (domain.getPreferences() != null) {
            var causes = domain.getPreferences().causes() != null
                    ? String.join(",", domain.getPreferences().causes())
                    : "";
            entity.setCauses(causes);
            entity.setAvailability(domain.getPreferences().availability());
            entity.setPreferredModality(domain.getPreferences().preferredModality());
        }
        return entity;
    }
}
