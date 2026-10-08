package com.bv.platform.participation.application.internal.commandservices;

import com.bv.platform.applications.interfaces.acl.ApplicationsContextFacade;
import com.bv.platform.participation.application.commandservices.ParticipationCommandService;
import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.commands.CompleteActividadCommand;
import com.bv.platform.participation.domain.model.commands.CreateActividadCommand;
import com.bv.platform.participation.domain.model.commands.RecordAttendanceCommand;
import com.bv.platform.participation.domain.model.commands.RecordBulkAttendanceCommand;
import com.bv.platform.participation.domain.model.commands.StartActividadCommand;
import com.bv.platform.participation.domain.repositories.ActividadRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteering.interfaces.acl.VolunteeringContextFacade;
import com.bv.platform.volunteers.interfaces.acl.VolunteersContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ParticipationCommandServiceImpl implements ParticipationCommandService {

    private final ActividadRepository repository;
    private final VolunteeringContextFacade volunteeringContextFacade;
    private final ApplicationsContextFacade applicationsContextFacade;
    private final VolunteersContextFacade volunteersContextFacade;

    public ParticipationCommandServiceImpl(ActividadRepository repository,
                                           VolunteeringContextFacade volunteeringContextFacade,
                                           ApplicationsContextFacade applicationsContextFacade,
                                           VolunteersContextFacade volunteersContextFacade) {
        this.repository = repository;
        this.volunteeringContextFacade = volunteeringContextFacade;
        this.applicationsContextFacade = applicationsContextFacade;
        this.volunteersContextFacade = volunteersContextFacade;
    }

    @Override
    public Result<ActividadVoluntariado, ApplicationError> handle(CreateActividadCommand command) {
        if (command.convocatoriaId() == null) {
            return Result.failure(ApplicationError.validationError("convocatoriaId", "El ID de la convocatoria es obligatorio."));
        }

        if (!volunteeringContextFacade.existsById(command.convocatoriaId())) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }

        var existing = repository.findByConvocatoriaId(command.convocatoriaId());
        if (existing.isPresent()) {
            return Result.success(existing.get());
        }

        String titulo = command.titulo();
        if (titulo == null || titulo.isBlank()) {
            titulo = volunteeringContextFacade.getConvocatoriaTitle(command.convocatoriaId());
        }

        LocalDate fecha = command.fechaActividad() != null ? command.fechaActividad() : LocalDate.now();
        var actividad = new ActividadVoluntariado(command.convocatoriaId(), titulo, fecha);

        var acceptedApplications = applicationsContextFacade.fetchAcceptedPostulacionesByConvocatoriaId(command.convocatoriaId());
        for (var p : acceptedApplications) {
            actividad.recordOrUpdateAttendance(p.getId(), p.getVolunteerId(), false, 0, "Postulante aceptado");
        }

        var saved = repository.save(actividad);
        return Result.success(saved);
    }

    @Override
    public Result<ActividadVoluntariado, ApplicationError> handle(StartActividadCommand command) {
        var existing = repository.findById(command.actividadId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Actividad", String.valueOf(command.actividadId())));
        }

        var actividad = existing.get();
        try {
            actividad.start();
            var saved = repository.save(actividad);
            return Result.success(saved);
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.businessRuleViolation("INVALID_STATE_TRANSITION", ex.getMessage()));
        }
    }

    @Override
    public Result<ActividadVoluntariado, ApplicationError> handle(CompleteActividadCommand command) {
        var existing = repository.findById(command.actividadId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Actividad", String.valueOf(command.actividadId())));
        }

        var actividad = existing.get();
        try {
            actividad.complete();

            for (var record : actividad.getAsistencias()) {
                if (record.isPresent() && record.getCertifiedHours() > 0) {
                    volunteersContextFacade.addAccumulatedHours(record.getVolunteerId(), record.getCertifiedHours());
                }
            }

            var saved = repository.save(actividad);
            return Result.success(saved);
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.businessRuleViolation("INVALID_STATE_TRANSITION", ex.getMessage()));
        }
    }

    @Override
    public Result<ActividadVoluntariado, ApplicationError> handle(RecordAttendanceCommand command) {
        var existing = repository.findById(command.actividadId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Actividad", String.valueOf(command.actividadId())));
        }

        var actividad = existing.get();
        actividad.recordOrUpdateAttendance(
                command.postulacionId(),
                command.volunteerId(),
                command.isPresent(),
                command.certifiedHours(),
                command.supervisorNotes()
        );

        if (actividad.isCompleted() && command.isPresent() && command.certifiedHours() > 0) {
            volunteersContextFacade.addAccumulatedHours(command.volunteerId(), command.certifiedHours());
        }

        var saved = repository.save(actividad);
        return Result.success(saved);
    }

    @Override
    public Result<ActividadVoluntariado, ApplicationError> handle(RecordBulkAttendanceCommand command) {
        var existing = repository.findById(command.actividadId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Actividad", String.valueOf(command.actividadId())));
        }

        var actividad = existing.get();
        if (command.attendances() != null) {
            for (var item : command.attendances()) {
                actividad.recordOrUpdateAttendance(
                        item.postulacionId(),
                        item.volunteerId(),
                        item.isPresent(),
                        item.certifiedHours(),
                        item.supervisorNotes()
                );
                if (actividad.isCompleted() && item.isPresent() && item.certifiedHours() > 0) {
                    volunteersContextFacade.addAccumulatedHours(item.volunteerId(), item.certifiedHours());
                }
            }
        }

        var saved = repository.save(actividad);
        return Result.success(saved);
    }
}
