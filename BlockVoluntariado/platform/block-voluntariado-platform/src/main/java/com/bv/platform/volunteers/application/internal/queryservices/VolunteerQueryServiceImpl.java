package com.bv.platform.volunteers.application.internal.queryservices;

import com.bv.platform.volunteers.application.queryservices.VolunteerQueryService;
import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerPreferencesQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByIdQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByUserIdQuery;
import com.bv.platform.volunteers.domain.model.valueobjects.VolunteerPreferences;
import com.bv.platform.volunteers.domain.repositories.VolunteerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VolunteerQueryServiceImpl implements VolunteerQueryService {

    private final VolunteerProfileRepository repository;

    public VolunteerQueryServiceImpl(VolunteerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<VolunteerProfile> handle(GetVolunteerProfileByIdQuery query) {
        return repository.findById(query.volunteerId());
    }

    @Override
    public Optional<VolunteerProfile> handle(GetVolunteerProfileByUserIdQuery query) {
        return repository.findByUserId(query.userId());
    }

    @Override
    public Optional<VolunteerPreferences> handle(GetVolunteerPreferencesQuery query) {
        return repository.findById(query.volunteerId()).map(VolunteerProfile::getPreferences);
    }
}
