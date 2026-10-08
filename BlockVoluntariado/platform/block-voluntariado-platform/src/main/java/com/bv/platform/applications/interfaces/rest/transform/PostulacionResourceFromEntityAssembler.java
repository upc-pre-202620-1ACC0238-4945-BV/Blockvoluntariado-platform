package com.bv.platform.applications.interfaces.rest.transform;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.interfaces.rest.resources.PostulacionResource;

public final class PostulacionResourceFromEntityAssembler {

    private PostulacionResourceFromEntityAssembler() {
    }

    public static PostulacionResource toResourceFromEntity(Postulacion entity) {
        if (entity == null) return null;
        return new PostulacionResource(
                entity.getId(),
                entity.getConvocatoriaId(),
                entity.getVolunteerId(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getRejectionReason(),
                entity.getAppliedAt()
        );
    }
}
