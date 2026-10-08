package com.bv.platform.volunteers.domain.repositories;

import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;

import java.util.List;
import java.util.Optional;

public interface VolunteerProfileRepository {
    List<VolunteerProfile> findAll();
    Optional<VolunteerProfile> findById(Long id);
    Optional<VolunteerProfile> findByUserId(Long userId);
    boolean existsByDniDocument(String dniDocument);
    boolean existsByUserId(Long userId);
    VolunteerProfile save(VolunteerProfile volunteerProfile);
}
