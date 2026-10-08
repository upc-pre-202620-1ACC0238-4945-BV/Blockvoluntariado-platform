package com.bv.platform.iam.interfaces.acl;

import com.bv.platform.iam.application.commandservices.UserCommandService;
import com.bv.platform.iam.application.queryservices.UserQueryService;
import com.bv.platform.iam.domain.model.commands.SignUpCommand;
import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.bv.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.bv.platform.shared.application.result.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    public Long createUser(String username, String password) {
        var signUpCommand = new SignUpCommand(username, password, List.of(Role.getDefaultRole()));
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }

    public Long createUser(String username, String password, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(username, password, roles);
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }

    public Long fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    public String fetchUsernameByUserId(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) return "";
        return result.get().getUsername();
    }
}
