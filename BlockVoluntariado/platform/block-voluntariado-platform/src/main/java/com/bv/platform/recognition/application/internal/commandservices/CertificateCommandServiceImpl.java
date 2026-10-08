package com.bv.platform.recognition.application.internal.commandservices;

import com.bv.platform.participation.interfaces.acl.ParticipationContextFacade;
import com.bv.platform.recognition.application.commandservices.CertificateCommandService;
import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.commands.IssueCertificateCommand;
import com.bv.platform.recognition.domain.repositories.DigitalCertificateRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteering.interfaces.acl.VolunteeringContextFacade;
import com.bv.platform.volunteers.interfaces.acl.VolunteersContextFacade;
import org.springframework.stereotype.Service;

@Service
public class CertificateCommandServiceImpl implements CertificateCommandService {

    private final DigitalCertificateRepository repository;
    private final VolunteersContextFacade volunteersContextFacade;
    private final VolunteeringContextFacade volunteeringContextFacade;
    private final ParticipationContextFacade participationContextFacade;

    public CertificateCommandServiceImpl(DigitalCertificateRepository repository,
                                         VolunteersContextFacade volunteersContextFacade,
                                         VolunteeringContextFacade volunteeringContextFacade,
                                         ParticipationContextFacade participationContextFacade) {
        this.repository = repository;
        this.volunteersContextFacade = volunteersContextFacade;
        this.volunteeringContextFacade = volunteeringContextFacade;
        this.participationContextFacade = participationContextFacade;
    }

    @Override
    public Result<DigitalCertificate, ApplicationError> handle(IssueCertificateCommand command) {
        if (command.volunteerId() == null) {
            return Result.failure(ApplicationError.validationError("volunteerId", "El ID del voluntario es obligatorio."));
        }
        if (command.convocatoriaId() == null) {
            return Result.failure(ApplicationError.validationError("convocatoriaId", "El ID de la convocatoria es obligatorio."));
        }

        if (!volunteersContextFacade.existsVolunteer(command.volunteerId())) {
            return Result.failure(ApplicationError.notFound("VolunteerProfile", String.valueOf(command.volunteerId())));
        }
        if (!volunteeringContextFacade.existsById(command.convocatoriaId())) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }

        if (repository.existsByVolunteerIdAndConvocatoriaId(command.volunteerId(), command.convocatoriaId())) {
            return Result.failure(ApplicationError.conflict("DigitalCertificate", "El certificado para esta convocatoria ya fue emitido previamente."));
        }

        int hours = (command.accreditedHours() != null && command.accreditedHours() > 0)
                ? command.accreditedHours()
                : participationContextFacade.getCertifiedHours(command.volunteerId(), command.convocatoriaId());

        if (hours <= 0) {
            hours = 4; // Default standard hours if none registered in field activity
        }

        var certificate = new DigitalCertificate(command.volunteerId(), command.convocatoriaId(), hours);
        var saved = repository.save(certificate);
        return Result.success(saved);
    }
}
