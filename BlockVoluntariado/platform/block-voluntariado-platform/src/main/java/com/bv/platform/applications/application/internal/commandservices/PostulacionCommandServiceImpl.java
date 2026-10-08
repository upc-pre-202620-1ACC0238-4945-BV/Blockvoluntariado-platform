package com.bv.platform.applications.application.internal.commandservices;

import com.bv.platform.applications.application.commandservices.PostulacionCommandService;
import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.model.commands.AcceptPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.CancelPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.CreatePostulacionCommand;
import com.bv.platform.applications.domain.model.commands.RejectPostulacionCommand;
import com.bv.platform.applications.domain.repositories.PostulacionRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteering.interfaces.acl.VolunteeringContextFacade;
import com.bv.platform.volunteers.interfaces.acl.VolunteersContextFacade;
import org.springframework.stereotype.Service;

@Service
public class PostulacionCommandServiceImpl implements PostulacionCommandService {

    private final PostulacionRepository repository;
    private final VolunteeringContextFacade volunteeringContextFacade;
    private final VolunteersContextFacade volunteersContextFacade;

    public PostulacionCommandServiceImpl(PostulacionRepository repository,
                                         VolunteeringContextFacade volunteeringContextFacade,
                                         VolunteersContextFacade volunteersContextFacade) {
        this.repository = repository;
        this.volunteeringContextFacade = volunteeringContextFacade;
        this.volunteersContextFacade = volunteersContextFacade;
    }

    @Override
    public Result<Postulacion, ApplicationError> handle(CreatePostulacionCommand command) {
        if (command.convocatoriaId() == null) {
            return Result.failure(ApplicationError.validationError("convocatoriaId", "El ID de la convocatoria es obligatorio."));
        }
        if (command.volunteerId() == null) {
            return Result.failure(ApplicationError.validationError("volunteerId", "El ID del voluntario es obligatorio."));
        }

        if (!volunteeringContextFacade.existsById(command.convocatoriaId())) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }
        if (!volunteeringContextFacade.isConvocatoriaOpen(command.convocatoriaId())) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "CONVOCATORIA_NOT_OPEN",
                    "La convocatoria no se encuentra en estado PUBLICADA."
            ));
        }
        if (!volunteeringContextFacade.hasAvailableVacancies(command.convocatoriaId())) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "NO_VACANCIES",
                    "La convocatoria no cuenta con vacantes disponibles."
            ));
        }
        if (!volunteersContextFacade.existsVolunteer(command.volunteerId())) {
            return Result.failure(ApplicationError.notFound("VolunteerProfile", String.valueOf(command.volunteerId())));
        }
        if (repository.existsByConvocatoriaIdAndVolunteerId(command.convocatoriaId(), command.volunteerId())) {
            return Result.failure(ApplicationError.conflict(
                    "Postulacion",
                    "El voluntario ya cuenta con una postulación registrada para esta convocatoria."
            ));
        }

        var postulacion = new Postulacion(command.convocatoriaId(), command.volunteerId());
        var saved = repository.save(postulacion);
        return Result.success(saved);
    }

    @Override
    public Result<Postulacion, ApplicationError> handle(AcceptPostulacionCommand command) {
        var existing = repository.findById(command.postulacionId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Postulacion", String.valueOf(command.postulacionId())));
        }

        var postulacion = existing.get();
        try {
            postulacion.accept();
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.businessRuleViolation("INVALID_STATE_TRANSITION", ex.getMessage()));
        }

        boolean incremented = volunteeringContextFacade.incrementOccupiedVacancies(postulacion.getConvocatoriaId());
        if (!incremented) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "NO_VACANCIES",
                    "No se pudo aceptar la postulación porque no quedan vacantes disponibles."
            ));
        }

        var saved = repository.save(postulacion);
        return Result.success(saved);
    }

    @Override
    public Result<Postulacion, ApplicationError> handle(RejectPostulacionCommand command) {
        var existing = repository.findById(command.postulacionId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Postulacion", String.valueOf(command.postulacionId())));
        }

        var postulacion = existing.get();
        if (postulacion.isAccepted()) {
            volunteeringContextFacade.decrementOccupiedVacancies(postulacion.getConvocatoriaId());
        }

        try {
            postulacion.reject(command.reason());
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.businessRuleViolation("INVALID_STATE_TRANSITION", ex.getMessage()));
        }

        var saved = repository.save(postulacion);
        return Result.success(saved);
    }

    @Override
    public Result<Postulacion, ApplicationError> handle(CancelPostulacionCommand command) {
        var existing = repository.findById(command.postulacionId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Postulacion", String.valueOf(command.postulacionId())));
        }

        var postulacion = existing.get();
        if (postulacion.isAccepted()) {
            volunteeringContextFacade.decrementOccupiedVacancies(postulacion.getConvocatoriaId());
        }

        try {
            postulacion.cancel();
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.businessRuleViolation("INVALID_STATE_TRANSITION", ex.getMessage()));
        }

        var saved = repository.save(postulacion);
        return Result.success(saved);
    }
}
