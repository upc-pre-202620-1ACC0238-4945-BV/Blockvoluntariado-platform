package com.bv.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;

public final class RolePersistenceAssembler {

    private RolePersistenceAssembler() {
    }

    public static Role toDomainFromPersistence(RolePersistenceEntity entity) {
        if (entity == null) return null;
        return new Role(entity.getId(), entity.getName());
    }

    public static RolePersistenceEntity toPersistenceFromDomain(Role role) {
        if (role == null) return null;
        var entity = new RolePersistenceEntity();
        if (role.getId() != null) {
            entity.setId(role.getId());
        }
        entity.setName(role.getName());
        return entity;
    }
}
