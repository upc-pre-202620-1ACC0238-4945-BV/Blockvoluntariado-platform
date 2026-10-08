package com.bv.platform.volunteers.interfaces.rest;

import com.bv.platform.iam.interfaces.acl.IamContextFacade;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bv.platform.volunteers.application.commandservices.VolunteerCommandService;
import com.bv.platform.volunteers.application.queryservices.VolunteerQueryService;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerPreferencesQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByIdQuery;
import com.bv.platform.volunteers.domain.model.queries.GetVolunteerProfileByUserIdQuery;
import com.bv.platform.volunteers.interfaces.rest.resources.CreateVolunteerProfileResource;
import com.bv.platform.volunteers.interfaces.rest.resources.UpdateVolunteerProfileResource;
import com.bv.platform.volunteers.interfaces.rest.resources.VolunteerPreferencesResource;
import com.bv.platform.volunteers.interfaces.rest.resources.VolunteerProfileResource;
import com.bv.platform.volunteers.interfaces.rest.transform.CreateVolunteerProfileCommandFromResourceAssembler;
import com.bv.platform.volunteers.interfaces.rest.transform.UpdateVolunteerPreferencesCommandFromResourceAssembler;
import com.bv.platform.volunteers.interfaces.rest.transform.UpdateVolunteerProfileCommandFromResourceAssembler;
import com.bv.platform.volunteers.interfaces.rest.transform.VolunteerProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/volunteers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Volunteer Management", description = "Gestión de perfiles y preferencias de estudiantes voluntarios")
@SecurityRequirement(name = "bearerAuth")
public class VolunteersController {

    private final VolunteerCommandService volunteerCommandService;
    private final VolunteerQueryService volunteerQueryService;
    private final IamContextFacade iamContextFacade;

    public VolunteersController(VolunteerCommandService volunteerCommandService,
                                VolunteerQueryService volunteerQueryService,
                                IamContextFacade iamContextFacade) {
        this.volunteerCommandService = volunteerCommandService;
        this.volunteerQueryService = volunteerQueryService;
        this.iamContextFacade = iamContextFacade;
    }

    @PostMapping("/profile")
    @Operation(summary = "Crear perfil de voluntario", description = "Registra los datos personales y académicos del estudiante universitario.")
    public ResponseEntity<?> createProfile(@Valid @RequestBody CreateVolunteerProfileResource resource) {
        var command = CreateVolunteerProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = volunteerCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                VolunteerProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{volunteerId}/profile")
    @Operation(summary = "Obtener perfil de voluntario", description = "Consulta la información detallada del perfil de un voluntario por su ID.")
    public ResponseEntity<VolunteerProfileResource> getProfileById(@PathVariable Long volunteerId) {
        var query = new GetVolunteerProfileByIdQuery(volunteerId);
        var profile = volunteerQueryService.handle(query);
        return profile.map(p -> ResponseEntity.ok(VolunteerProfileResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{volunteerId}/profile")
    @Operation(summary = "Actualizar perfil de voluntario", description = "Actualiza datos personales y académicos del estudiante.")
    public ResponseEntity<?> updateProfile(@PathVariable Long volunteerId,
                                           @Valid @RequestBody UpdateVolunteerProfileResource resource) {
        var command = UpdateVolunteerProfileCommandFromResourceAssembler.toCommandFromResource(volunteerId, resource);
        var result = volunteerCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                VolunteerProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/{volunteerId}/preferences")
    @Operation(summary = "Obtener preferencias del voluntario", description = "Consulta las causas de interés y disponibilidad para recomendación de voluntariados.")
    public ResponseEntity<VolunteerPreferencesResource> getPreferences(@PathVariable Long volunteerId) {
        var query = new GetVolunteerPreferencesQuery(volunteerId);
        var preferences = volunteerQueryService.handle(query);
        return preferences.map(pref -> ResponseEntity.ok(new VolunteerPreferencesResource(
                pref.causes(),
                pref.availability(),
                pref.preferredModality()
        ))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{volunteerId}/preferences")
    @Operation(summary = "Actualizar preferencias del voluntario", description = "Modifica los intereses de causas y disponibilidad horaria del voluntario.")
    public ResponseEntity<?> updatePreferences(@PathVariable Long volunteerId,
                                               @RequestBody VolunteerPreferencesResource resource) {
        var command = UpdateVolunteerPreferencesCommandFromResourceAssembler.toCommandFromResource(volunteerId, resource);
        var result = volunteerCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                VolunteerProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/by-user/{userId}")
    @Operation(summary = "Obtener perfil por User ID", description = "Permite recuperar el perfil de voluntario asociado a una cuenta de usuario.")
    public ResponseEntity<VolunteerProfileResource> getProfileByUserId(@PathVariable Long userId) {
        var query = new GetVolunteerProfileByUserIdQuery(userId);
        var profile = volunteerQueryService.handle(query);
        return profile.map(p -> ResponseEntity.ok(VolunteerProfileResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener mi perfil", description = "Recupera el perfil de voluntario del usuario autenticado en la sesión actual.")
    public ResponseEntity<VolunteerProfileResource> getMyProfile(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).build();
        }
        Long userId = iamContextFacade.fetchUserIdByUsername(authentication.getName());
        if (userId == null || userId == 0L) {
            return ResponseEntity.notFound().build();
        }
        var query = new GetVolunteerProfileByUserIdQuery(userId);
        var profile = volunteerQueryService.handle(query);
        return profile.map(p -> ResponseEntity.ok(VolunteerProfileResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
