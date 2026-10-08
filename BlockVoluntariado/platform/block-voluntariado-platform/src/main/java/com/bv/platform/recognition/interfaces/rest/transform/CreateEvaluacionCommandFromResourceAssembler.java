package com.bv.platform.recognition.interfaces.rest.transform;

import com.bv.platform.recognition.domain.model.commands.CreateEvaluacionCommand;
import com.bv.platform.recognition.domain.model.valueobjects.TipoEvaluador;
import com.bv.platform.recognition.interfaces.rest.resources.CreateEvaluacionResource;

public final class CreateEvaluacionCommandFromResourceAssembler {

    private CreateEvaluacionCommandFromResourceAssembler() {
    }

    public static CreateEvaluacionCommand toCommandFromResource(Long resolvedEvaluadorId,
                                                               Long evaluadoId,
                                                               TipoEvaluador tipoEvaluador,
                                                               CreateEvaluacionResource resource) {
        Long evaluadorId = (resource != null && resource.evaluadorId() != null && resource.evaluadorId() > 0)
                ? resource.evaluadorId()
                : resolvedEvaluadorId;

        int score = resource != null && resource.score() != null ? resource.score() : 5;
        String feedback = resource != null ? resource.feedback() : "";

        return new CreateEvaluacionCommand(
                evaluadorId,
                evaluadoId,
                tipoEvaluador,
                score,
                feedback
        );
    }
}
