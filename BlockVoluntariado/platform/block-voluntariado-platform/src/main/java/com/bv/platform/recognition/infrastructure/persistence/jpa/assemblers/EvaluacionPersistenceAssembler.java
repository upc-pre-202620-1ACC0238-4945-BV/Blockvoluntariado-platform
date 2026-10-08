package com.bv.platform.recognition.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.infrastructure.persistence.jpa.entities.EvaluacionPersistenceEntity;

public final class EvaluacionPersistenceAssembler {

    private EvaluacionPersistenceAssembler() {
    }

    public static Evaluacion toDomainFromPersistence(EvaluacionPersistenceEntity entity) {
        if (entity == null) return null;
        return new Evaluacion(
                entity.getId(),
                entity.getEvaluadorId(),
                entity.getEvaluadoId(),
                entity.getTipoEvaluador(),
                entity.getScore(),
                entity.getFeedback(),
                entity.getFecha()
        );
    }

    public static EvaluacionPersistenceEntity toPersistenceFromDomain(Evaluacion domain) {
        if (domain == null) return null;
        var entity = new EvaluacionPersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setEvaluadorId(domain.getEvaluadorId());
        entity.setEvaluadoId(domain.getEvaluadoId());
        entity.setTipoEvaluador(domain.getTipoEvaluador());
        entity.setScore(domain.getScore());
        entity.setFeedback(domain.getFeedback());
        entity.setFecha(domain.getFecha());
        return entity;
    }
}
