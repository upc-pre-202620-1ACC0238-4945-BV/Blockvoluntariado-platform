package com.bv.platform.iam.interfaces.rest.transform;

import com.bv.platform.iam.domain.model.aggregates.User;
import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

public final class AuthenticatedUserResourceFromEntityAssembler {

    private AuthenticatedUserResourceFromEntityAssembler() {
    }

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token, roles);
    }
}
