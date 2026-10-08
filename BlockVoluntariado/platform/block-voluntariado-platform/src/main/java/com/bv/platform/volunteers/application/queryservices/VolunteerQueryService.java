package com.bv.platform.volunteers.application.queryservices;

import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerPreferencesQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByIdQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByUserIdQuery;
import com.bv.platform.volunteers.domain.model.valueobjects.VolunteerPreferences;

import java.util.Optional;

public interface VolunteerQueryService {
    Optional<VolunteerProfile> handle(GetVolunteerProfileByIdQuery query);
    Optional<VolunteerProfile> handle(GetVolunteerProfileByUserIdQuery query);
    Optional<VolunteerPreferences> handle(GetVolunteerPreferencesQuery query);
}
