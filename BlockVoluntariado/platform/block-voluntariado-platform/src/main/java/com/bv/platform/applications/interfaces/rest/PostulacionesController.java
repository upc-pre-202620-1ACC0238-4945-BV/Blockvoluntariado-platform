package com.bv.platform.applications.interfaces.rest;

import com.bv.platform.applications.application.commandservices.PostulacionCommandService;
import com.bv.platform.applications.application.queryservices.PostulacionQueryService;
import com.bv.platform.applications.domain.model.commands.AcceptPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.CancelPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.RejectPostulacionCommand;
import com.bv.platform.applications.domain.model.queries.GetPostulacionByIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByConvocatoriaIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByVolunteerIdQuery;
import com.bv.platform.applications.interfaces.rest.resources.CreatePostulacionResource;
import com.bv.platform.applications.interfaces.rest.resources.PostulacionResource;
import com.bv.platform.applications.interfaces.rest.resources.RejectPostulacionResource;
import com.bv.platform.applications.interfaces.rest.transform.CreatePostulacionCommandFromResourceAssembler;
import com.bv.platform.applications.interfaces.rest.transform.PostulacionResourceFromEntityAssembler;
import com.bv.platform.iam.interfaces.acl.IamContextFacade;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bv.platform.volunteers.interfaces.acl.VolunteersContextFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Application Management", description = "Gestión de postulaciones y emparejamiento de estudiantes con voluntariados")
@SecurityRequirement(name = "bearerAuth")
public class PostulacionesController {

    private final PostulacionCommandService commandService;
    private final PostulacionQueryService queryService;
    private final IamContextFacade iamContextFacade;
    private final VolunteersContextFacade volunteersContextFacade;

    public PostulacionesController(PostulacionCommandService commandService,
                                   PostulacionQueryService queryService,
                                   IamContextFacade iamContextFacade,
                                   VolunteersContextFacade volunteersContextFacade) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.iamContextFacade = iamContextFacade;
        this.volunteersContextFacade = volunteersContextFacade;
    }

    @PostMapping("/api/v1/convocatorias/{convocatoriaId}/postulaciones")
    @Operation(summary = "Postular a una convocatoria", description = "Registra la postulación de un estudiante a una convocatoria disponible.")
    public ResponseEntity<?> applyToConvocatoria(@PathVariable Long convocatoriaId,
                                                @RequestBody(required = false) CreatePostulacionResource resource,
                                                Authentication authentication) {
        Long resolvedVolunteerId = null;
        if (resource != null && resource.volunteerId() != null && resource.volunteerId() > 0) {
            resolvedVolunteerId = resource.volunteerId();
        } else if (authentication != null && authentication.isAuthenticated()) {
            var userId = iamContextFacade.fetchUserIdByUsername(authentication.getName());
            if (userId != null && userId > 0) {
                resolvedVolunteerId = volunteersContextFacade.getVolunteerIdByUserId(userId);
            }
        }

        var command = CreatePostulacionCommandFromResourceAssembler.toCommandFromResource(
                convocatoriaId,
                resolvedVolunteerId,
                resource
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostulacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/api/v1/convocatorias/{convocatoriaId}/postulantes")
    @Operation(summary = "Listar postulantes de una convocatoria", description = "Obtiene la lista de estudiantes postulados a una convocatoria específica (vista ONG).")
    public ResponseEntity<List<PostulacionResource>> getPostulantesByConvocatoria(@PathVariable Long convocatoriaId) {
        var query = new GetPostulacionesByConvocatoriaIdQuery(convocatoriaId);
        var postulaciones = queryService.handle(query);
        var resources = postulaciones.stream()
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/api/v1/postulaciones/{postulacionId}/aceptar")
    @Operation(summary = "Aceptar postulación", description = "La ONG acepta a un voluntario para participar en la convocatoria e incrementa los cupos ocupados.")
    public ResponseEntity<?> acceptPostulacion(@PathVariable Long postulacionId) {
        var command = new AcceptPostulacionCommand(postulacionId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostulacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/api/v1/postulaciones/{postulacionId}/rechazar")
    @Operation(summary = "Rechazar postulación", description = "La ONG rechaza la postulación indicando opcionalmente un motivo justificado.")
    public ResponseEntity<?> rejectPostulacion(@PathVariable Long postulacionId,
                                               @RequestBody(required = false) RejectPostulacionResource resource) {
        String reason = resource != null ? resource.reason() : "No cumple con el perfil solicitado";
        var command = new RejectPostulacionCommand(postulacionId, reason);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostulacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/api/v1/postulaciones/{postulacionId}/cancelar")
    @Operation(summary = "Cancelar postulación", description = "El voluntario desiste de su postulación.")
    public ResponseEntity<?> cancelPostulacion(@PathVariable Long postulacionId) {
        var command = new CancelPostulacionCommand(postulacionId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostulacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/api/v1/volunteers/{volunteerId}/postulaciones")
    @Operation(summary = "Mis postulaciones", description = "Lista el historial completo de postulaciones de un voluntario específico.")
    public ResponseEntity<List<PostulacionResource>> getPostulacionesByVolunteer(@PathVariable Long volunteerId) {
        var query = new GetPostulacionesByVolunteerIdQuery(volunteerId);
        var postulaciones = queryService.handle(query);
        var resources = postulaciones.stream()
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/api/v1/postulaciones/{postulacionId}")
    @Operation(summary = "Detalle de una postulación", description = "Obtiene los detalles específicos de una postulación por su ID.")
    public ResponseEntity<PostulacionResource> getPostulacionById(@PathVariable Long postulacionId) {
        var query = new GetPostulacionByIdQuery(postulacionId);
        var postulacion = queryService.handle(query);
        return postulacion
                .map(PostulacionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
