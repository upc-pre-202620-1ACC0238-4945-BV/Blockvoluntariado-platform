package com.bv.platform.recognition.application.internal.queryservices;

import com.bv.platform.recognition.application.queryservices.GamificationQueryService;
import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.valueobjects.GamificationBadge;
import com.bv.platform.recognition.domain.repositories.DigitalCertificateRepository;
import com.bv.platform.recognition.interfaces.rest.resources.GamificationProfileResource;
import com.bv.platform.recognition.interfaces.rest.resources.VolunteerHistoryItemResource;
import com.bv.platform.volunteering.interfaces.acl.VolunteeringContextFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GamificationQueryServiceImpl implements GamificationQueryService {

    private final DigitalCertificateRepository certificateRepository;
    private final VolunteeringContextFacade volunteeringContextFacade;

    public GamificationQueryServiceImpl(DigitalCertificateRepository certificateRepository,
                                        VolunteeringContextFacade volunteeringContextFacade) {
        this.certificateRepository = certificateRepository;
        this.volunteeringContextFacade = volunteeringContextFacade;
    }

    @Override
    public GamificationProfileResource getGamificationProfile(Long volunteerId) {
        var certificates = certificateRepository.findByVolunteerId(volunteerId);
        int totalHours = certificates.stream().mapToInt(DigitalCertificate::getAccreditedHours).sum();

        String level;
        int levelNumber;
        List<GamificationBadge> badges = new ArrayList<>();
        badges.add(GamificationBadge.bronze());

        if (totalHours >= 100) {
            level = "Líder Social";
            levelNumber = 4;
            badges.add(GamificationBadge.silver());
            badges.add(GamificationBadge.gold());
            badges.add(GamificationBadge.platinum());
        } else if (totalHours >= 50) {
            level = "Comprometido";
            levelNumber = 3;
            badges.add(GamificationBadge.silver());
            badges.add(GamificationBadge.gold());
        } else if (totalHours >= 20) {
            level = "Activo";
            levelNumber = 2;
            badges.add(GamificationBadge.silver());
        } else {
            level = "Iniciado";
            levelNumber = 1;
        }

        return new GamificationProfileResource(
                volunteerId,
                totalHours,
                level,
                levelNumber,
                badges,
                certificates.size()
        );
    }

    @Override
    public List<VolunteerHistoryItemResource> getVolunteerHistory(Long volunteerId) {
        var certificates = certificateRepository.findByVolunteerId(volunteerId);
        return certificates.stream().map(c -> {
            String title = volunteeringContextFacade.getConvocatoriaTitle(c.getConvocatoriaId());
            Long orgId = volunteeringContextFacade.getConvocatoriaOrganizationId(c.getConvocatoriaId());
            String orgName = orgId != null ? "Organización #" + orgId : "Organización Aliada";

            return new VolunteerHistoryItemResource(
                    c.getConvocatoriaId(),
                    title,
                    orgName,
                    c.getAccreditedHours(),
                    c.getVerificationHash(),
                    c.getPdfDownloadUrl(),
                    c.getIssuedAt()
            );
        }).toList();
    }
}
