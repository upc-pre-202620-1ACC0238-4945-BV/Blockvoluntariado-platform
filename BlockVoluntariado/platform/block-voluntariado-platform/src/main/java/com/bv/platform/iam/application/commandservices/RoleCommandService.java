package com.bv.platform.iam.application.commandservices;

import com.bv.platform.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
