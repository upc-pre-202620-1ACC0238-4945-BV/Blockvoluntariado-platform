package com.bv.platform.recognition.interfaces.rest.resources;

import com.bv.platform.recognition.domain.model.valueobjects.GamificationBadge;

import java.util.List;

public record GamificationProfileResource(
        Long volunteerId,
        int totalHours,
        String level,
        int levelNumber,
        List<GamificationBadge> badges,
        int totalCertificates
) {}
