package com.bv.platform.volunteers.application.commandservices;

import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.model.commands.CreateVolunteerProfileCommand;
import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerPreferencesCommand;
import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerProfileCommand;

public interface VolunteerCommandService {
    Result<VolunteerProfile, ApplicationError> handle(CreateVolunteerProfileCommand command);
    Result<VolunteerProfile, ApplicationError> handle(UpdateVolunteerProfileCommand command);
    Result<VolunteerProfile, ApplicationError> handle(UpdateVolunteerPreferencesCommand command);
    void addAccumulatedHours(Long volunteerId, int hours);
}
