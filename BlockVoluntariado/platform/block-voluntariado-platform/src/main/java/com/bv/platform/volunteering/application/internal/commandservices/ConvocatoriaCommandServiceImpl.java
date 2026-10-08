package com.bv.platform.volunteering.application.internal.commandservices;

import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteering.application.commandservices.ConvocatoriaCommandService;
import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.commands.CloseConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.CreateConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.PublishConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.UpdateConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.valueobjects.Horario;
import com.bv.platform.volunteering.domain.model.valueobjects.Ubicacion;
import com.bv.platform.volunteering.domain.repositories.ConvocatoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConvocatoriaCommandServiceImpl implements ConvocatoriaCommandService {

    private final ConvocatoriaRepository repository;

    public ConvocatoriaCommandServiceImpl(ConvocatoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Convocatoria, ApplicationError> handle(CreateConvocatoriaCommand command) {
        if (command.organizationId() == null) {
            return Result.failure(ApplicationError.validationError("organizationId", "El ID de la organización es obligatorio."));
        }
        if (command.title() == null || command.title().isBlank()) {
            return Result.failure(ApplicationError.validationError("title", "El título de la convocatoria es obligatorio."));
        }
        if (command.totalVacancies() <= 0) {
            return Result.failure(ApplicationError.validationError("totalVacancies", "El número total de vacantes debe ser mayor a cero."));
        }

        try {
            var horario = new Horario(
                    command.startDate(),
                    command.endDate(),
                    command.startTime(),
                    command.endTime()
            );
            var ubicacion = new Ubicacion(
                    command.district(),
                    command.addressLine(),
                    command.latitude(),
                    command.longitude()
            );

            var convocatoria = new Convocatoria(
                    command.organizationId(),
                    command.title(),
                    command.description(),
                    command.causeType(),
                    command.totalVacancies(),
                    horario,
                    ubicacion
            );

            var saved = repository.save(convocatoria);
            return Result.success(saved);
        } catch (IllegalArgumentException ex) {
            return Result.failure(ApplicationError.validationError("horario", ex.getMessage()));
        }
    }

    @Override
    public Result<Convocatoria, ApplicationError> handle(UpdateConvocatoriaCommand command) {
        var existing = repository.findById(command.convocatoriaId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }

        var convocatoria = existing.get();
        try {
            var horario = new Horario(
                    command.startDate(),
                    command.endDate(),
                    command.startTime(),
                    command.endTime()
            );
            var ubicacion = new Ubicacion(
                    command.district(),
                    command.addressLine(),
                    command.latitude(),
                    command.longitude()
            );

            convocatoria.update(
                    command.title(),
                    command.description(),
                    command.causeType(),
                    command.totalVacancies(),
                    horario,
                    ubicacion
            );

            var saved = repository.save(convocatoria);
            return Result.success(saved);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return Result.failure(ApplicationError.validationError("convocatoria", ex.getMessage()));
        }
    }

    @Override
    public Result<Convocatoria, ApplicationError> handle(PublishConvocatoriaCommand command) {
        var existing = repository.findById(command.convocatoriaId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }

        var convocatoria = existing.get();
        try {
            convocatoria.publish();
            var saved = repository.save(convocatoria);
            return Result.success(saved);
        } catch (IllegalStateException ex) {
            return Result.failure(ApplicationError.validationError("status", ex.getMessage()));
        }
    }

    @Override
    public Result<Convocatoria, ApplicationError> handle(CloseConvocatoriaCommand command) {
        var existing = repository.findById(command.convocatoriaId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(command.convocatoriaId())));
        }

        var convocatoria = existing.get();
        convocatoria.close();
        var saved = repository.save(convocatoria);
        return Result.success(saved);
    }

    @Override
    public Result<Convocatoria, ApplicationError> incrementOccupiedVacancies(Long convocatoriaId) {
        var existing = repository.findById(convocatoriaId);
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(convocatoriaId)));
        }

        var convocatoria = existing.get();
        if (!convocatoria.incrementOccupiedVacancies()) {
            return Result.failure(ApplicationError.validationError("vacancies", "No hay vacantes disponibles en esta convocatoria."));
        }

        var saved = repository.save(convocatoria);
        return Result.success(saved);
    }

    @Override
    public Result<Convocatoria, ApplicationError> decrementOccupiedVacancies(Long convocatoriaId) {
        var existing = repository.findById(convocatoriaId);
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Convocatoria", String.valueOf(convocatoriaId)));
        }

        var convocatoria = existing.get();
        convocatoria.decrementOccupiedVacancies();
        var saved = repository.save(convocatoria);
        return Result.success(saved);
    }
}
