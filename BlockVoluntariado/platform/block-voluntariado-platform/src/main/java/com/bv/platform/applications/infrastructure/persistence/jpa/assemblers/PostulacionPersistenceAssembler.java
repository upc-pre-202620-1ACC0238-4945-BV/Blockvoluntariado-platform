package com.bv.platform.applications.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.infrastructure.persistence.jpa.entities.PostulacionPersistenceEntity;

public final class PostulacionPersistenceAssembler {

    private PostulacionPersistenceAssembler() {
    }

    public static Postulacion toDomainFromPersistence(PostulacionPersistenceEntity entity) {
        if (entity == null) return null;
        return new Postulacion(
                entity.getId(),
                entity.getConvocatoriaId(),
                entity.getVolunteerId(),
                entity.getStatus(),
                entity.getRejectionReason(),
                entity.getAppliedAt()
        );
    }

    public static PostulacionPersistenceEntity toPersistenceFromDomain(Postulacion domain) {
        if (domain == null) return null;
        var entity = new PostulacionPersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setConvocatoriaId(domain.getConvocatoriaId());
        entity.setVolunteerId(domain.getVolunteerId());
        entity.setStatus(domain.getStatus());
        entity.setRejectionReason(domain.getRejectionReason());
        entity.setAppliedAt(domain.getAppliedAt());
        return entity;
    }
}
