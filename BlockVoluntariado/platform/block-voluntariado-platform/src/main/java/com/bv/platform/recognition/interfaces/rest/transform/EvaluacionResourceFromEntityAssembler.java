package com.bv.platform.recognition.interfaces.rest.transform;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.interfaces.rest.resources.EvaluacionResource;

public final class EvaluacionResourceFromEntityAssembler {

    private EvaluacionResourceFromEntityAssembler() {
    }

    public static EvaluacionResource toResourceFromEntity(Evaluacion entity) {
        if (entity == null) return null;
        return new EvaluacionResource(
                entity.getId(),
                entity.getEvaluadorId(),
                entity.getEvaluadoId(),
                entity.getTipoEvaluador() != null ? entity.getTipoEvaluador().name() : null,
                entity.getScore(),
                entity.getFeedback(),
                entity.getFecha()
        );
    }
}
