package com.bv.platform.volunteers.interfaces.acl;

import com.bv.platform.volunteers.application.commandservices.VolunteerCommandService;
import com.bv.platform.volunteers.application.queryservices.VolunteerQueryService;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByIdQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByUserIdQuery;
import org.springframework.stereotype.Component;

@Component
public class VolunteersContextFacade {

    private final VolunteerQueryService queryService;
    private final VolunteerCommandService commandService;

    public VolunteersContextFacade(VolunteerQueryService queryService, VolunteerCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    public boolean existsVolunteer(Long volunteerId) {
        return queryService.handle(new GetVolunteerProfileByIdQuery(volunteerId)).isPresent();
    }

    public Long getVolunteerIdByUserId(Long userId) {
        return queryService.handle(new GetVolunteerProfileByUserIdQuery(userId))
                .map(profile -> profile.getId())
                .orElse(null);
    }

    public void addAccumulatedHours(Long volunteerId, int hours) {
        commandService.addAccumulatedHours(volunteerId, hours);
    }
}
