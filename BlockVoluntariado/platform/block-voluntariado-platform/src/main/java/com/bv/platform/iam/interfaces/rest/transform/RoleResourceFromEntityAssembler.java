package com.bv.platform.iam.interfaces.rest.transform;

import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.interfaces.rest.resources.RoleResource;

public final class RoleResourceFromEntityAssembler {

    private RoleResourceFromEntityAssembler() {
    }

    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
