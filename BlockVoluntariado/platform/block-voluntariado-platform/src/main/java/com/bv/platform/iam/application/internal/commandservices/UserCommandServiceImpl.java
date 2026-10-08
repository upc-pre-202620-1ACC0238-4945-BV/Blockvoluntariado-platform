package com.bv.platform.iam.application.internal.commandservices;

import com.bv.platform.iam.application.commandservices.UserCommandService;
import com.bv.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.bv.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.bv.platform.iam.domain.model.aggregates.User;
import com.bv.platform.iam.domain.model.commands.PasswordRecoveryCommand;
import com.bv.platform.iam.domain.model.commands.SignInCommand;
import com.bv.platform.iam.domain.model.commands.SignUpCommand;
import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.domain.repositories.RoleRepository;
import com.bv.platform.iam.domain.repositories.UserRepository;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Result<Pair<User, String>, ApplicationError> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.username()));
        }
        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid username or password"));
        }
        var token = tokenService.generateToken(user.get().getUsername());
        return Result.success(Pair.of(user.get(), token));
    }

    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict("User", "Username or email already exists"));
        }
        var requestedRoles = command.roles() == null || command.roles().isEmpty()
                ? List.of(Role.getDefaultRole())
                : command.roles();

        var roles = requestedRoles.stream()
                .map(role -> roleRepository.findByName(role.getName()))
                .toList();

        if (roles.stream().anyMatch(Optional::isEmpty)) {
            return Result.failure(ApplicationError.notFound("Role", "one or more role names"));
        }

        var resolvedRoles = roles.stream()
                .map(Optional::get)
                .toList();

        var user = new User(command.username(), hashingService.encode(command.password()), resolvedRoles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username())
                .<Result<User, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.unexpected("sign-up", "Created user could not be reloaded")));
    }

    @Override
    public Result<String, ApplicationError> handle(PasswordRecoveryCommand command) {
        var user = userRepository.findByUsername(command.email());
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.email()));
        }
        return Result.success("Password recovery instructions sent to " + command.email());
    }
}
