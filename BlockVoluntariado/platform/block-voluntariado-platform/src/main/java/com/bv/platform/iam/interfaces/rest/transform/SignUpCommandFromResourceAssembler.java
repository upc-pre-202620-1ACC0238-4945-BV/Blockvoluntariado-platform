package com.bv.platform.iam.interfaces.rest.transform;

import com.bv.platform.iam.domain.model.commands.SignUpCommand;
import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public final class SignUpCommandFromResourceAssembler {

    private SignUpCommandFromResourceAssembler() {
    }

    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null
                ? resource.roles().stream().map(Role::toRoleFromName).toList()
                : new ArrayList<Role>();
        return new SignUpCommand(resource.email(), resource.password(), roles);
    }
}
