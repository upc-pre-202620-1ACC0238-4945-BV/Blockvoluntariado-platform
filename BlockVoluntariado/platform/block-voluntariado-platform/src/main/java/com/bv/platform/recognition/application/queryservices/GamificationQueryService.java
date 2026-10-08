package com.bv.platform.recognition.application.queryservices;

import com.bv.platform.recognition.interfaces.rest.resources.GamificationProfileResource;
import com.bv.platform.recognition.interfaces.rest.resources.VolunteerHistoryItemResource;

import java.util.List;

public interface GamificationQueryService {
    GamificationProfileResource getGamificationProfile(Long volunteerId);
    List<VolunteerHistoryItemResource> getVolunteerHistory(Long volunteerId);
}
