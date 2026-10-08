package com.bv.platform.iam.interfaces.rest;

import com.bv.platform.iam.application.commandservices.UserCommandService;
import com.bv.platform.iam.domain.model.commands.PasswordRecoveryCommand;
import com.bv.platform.iam.domain.model.commands.SignInCommand;
import com.bv.platform.iam.domain.model.commands.SignUpCommand;
import com.bv.platform.iam.domain.model.entities.Role;
import com.bv.platform.iam.domain.model.valueobjects.Roles;
import com.bv.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.bv.platform.iam.interfaces.rest.resources.PasswordRecoveryResource;
import com.bv.platform.iam.interfaces.rest.resources.SignInResource;
import com.bv.platform.iam.interfaces.rest.resources.SignUpResource;
import com.bv.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.bv.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.bv.platform.shared.interfaces.rest.resources.MessageResource;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Authentication and authorization controller with endpoints specified in BlockVoluntariado bounded context.
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Endpoints de autenticación, registro y recuperación de acceso")
public class AuthController {

    private final UserCommandService userCommandService;

    public AuthController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/register/student")
    @Operation(summary = "Registrar nuevo estudiante", description = "Registra una cuenta de usuario con rol de Estudiante Universitario.")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody SignUpResource resource) {
        var command = new SignUpCommand(resource.email(), resource.password(), List.of(new Role(Roles.ROLE_STUDENT)));
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, UserResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PostMapping("/register/ong")
    @Operation(summary = "Registrar nueva ONG / Organización", description = "Registra una cuenta de usuario con rol de Representante de ONG.")
    public ResponseEntity<?> registerOrganization(@Valid @RequestBody SignUpResource resource) {
        var command = new SignUpCommand(resource.email(), resource.password(), List.of(new Role(Roles.ROLE_ORGANIZATION)));
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, UserResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna el token JWT de acceso.")
    public ResponseEntity<?> login(@Valid @RequestBody SignInResource resource) {
        var command = new SignInCommand(resource.username(), resource.password());
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                pair -> AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(pair.getFirst(), pair.getSecond()),
                HttpStatus.OK
        );
    }

    @PostMapping("/password-recovery")
    @Operation(summary = "Recuperación de contraseña", description = "Inicia el flujo de recuperación de contraseña para el correo indicado.")
    public ResponseEntity<?> passwordRecovery(@Valid @RequestBody PasswordRecoveryResource resource) {
        var command = new PasswordRecoveryCommand(resource.email());
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, MessageResource::new, HttpStatus.OK);
    }
}
