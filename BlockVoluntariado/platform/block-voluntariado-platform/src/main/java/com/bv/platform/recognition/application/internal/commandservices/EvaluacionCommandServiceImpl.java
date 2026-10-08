package com.bv.platform.recognition.application.internal.commandservices;

import com.bv.platform.recognition.application.commandservices.EvaluacionCommandService;
import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.domain.model.commands.CreateEvaluacionCommand;
import com.bv.platform.recognition.domain.repositories.EvaluacionRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionCommandServiceImpl implements EvaluacionCommandService {

    private final EvaluacionRepository repository;

    public EvaluacionCommandServiceImpl(EvaluacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Evaluacion, ApplicationError> handle(CreateEvaluacionCommand command) {
        if (command.evaluadorId() == null) {
            return Result.failure(ApplicationError.validationError("evaluadorId", "El ID del evaluador es obligatorio."));
        }
        if (command.evaluadoId() == null) {
            return Result.failure(ApplicationError.validationError("evaluadoId", "El ID del evaluado es obligatorio."));
        }
        if (command.score() < 1 || command.score() > 5) {
            return Result.failure(ApplicationError.validationError("score", "La calificación debe ser un valor entero entre 1 y 5 estrellas."));
        }

        var evaluacion = new Evaluacion(
                command.evaluadorId(),
                command.evaluadoId(),
                command.tipoEvaluador(),
                command.score(),
                command.feedback()
        );

        var saved = repository.save(evaluacion);
        return Result.success(saved);
    }
}
