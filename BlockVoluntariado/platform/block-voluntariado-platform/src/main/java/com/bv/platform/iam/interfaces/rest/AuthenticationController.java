package com.bv.platform.iam.interfaces.rest;

import com.bv.platform.iam.application.commandservices.UserCommandService;
import com.bv.platform.iam.interfaces.rest.resources.SignInResource;
import com.bv.platform.iam.interfaces.rest.resources.SignUpResource;
import com.bv.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.bv.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.bv.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.bv.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
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

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication Standard", description = "Endpoints estándar de sign-in y sign-up")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in estándar", description = "Autentica al usuario por credenciales.")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                pair -> AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(pair.getFirst(), pair.getSecond()),
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up estándar", description = "Registra un usuario genérico en el sistema.")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, UserResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }
}
