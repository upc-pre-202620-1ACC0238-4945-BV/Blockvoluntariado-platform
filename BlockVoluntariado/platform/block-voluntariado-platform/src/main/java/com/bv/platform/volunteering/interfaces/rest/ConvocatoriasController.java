package com.bv.platform.volunteering.interfaces.rest;

import com.bv.platform.iam.interfaces.acl.IamContextFacade;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bv.platform.volunteering.application.commandservices.ConvocatoriaCommandService;
import com.bv.platform.volunteering.application.queryservices.ConvocatoriaQueryService;
import com.bv.platform.volunteering.domain.model.commands.CloseConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.PublishConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriaByIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriasByOrganizationIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetFilteredConvocatoriasQuery;
import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;
import com.bv.platform.volunteering.interfaces.rest.resources.ConvocatoriaResource;
import com.bv.platform.volunteering.interfaces.rest.resources.CreateConvocatoriaResource;
import com.bv.platform.volunteering.interfaces.rest.resources.UpdateConvocatoriaResource;
import com.bv.platform.volunteering.interfaces.rest.transform.CreateConvocatoriaCommandFromResourceAssembler;
import com.bv.platform.volunteering.interfaces.rest.transform.ConvocatoriaResourceFromEntityAssembler;
import com.bv.platform.volunteering.interfaces.rest.transform.UpdateConvocatoriaCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/convocatorias", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Volunteering Management", description = "Catálogo y ciclo de vida de convocatorias de voluntariado")
public class ConvocatoriasController {

    private final ConvocatoriaCommandService commandService;
    private final ConvocatoriaQueryService queryService;
    private final IamContextFacade iamContextFacade;

    public ConvocatoriasController(ConvocatoriaCommandService commandService,
                                  ConvocatoriaQueryService queryService,
                                  IamContextFacade iamContextFacade) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.iamContextFacade = iamContextFacade;
    }

    @PostMapping
    @Operation(summary = "Crear borrador de convocatoria", description = "Crea una nueva convocatoria en estado BORRADOR para una organización social / ONG.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createConvocatoria(@Valid @RequestBody CreateConvocatoriaResource resource,
                                                Authentication authentication) {
        Long orgId = resource.organizationId();
        if ((orgId == null || orgId <= 0) && authentication != null && authentication.isAuthenticated()) {
            var userId = iamContextFacade.fetchUserIdByUsername(authentication.getName());
            if (userId != null && userId > 0) {
                orgId = userId;
            }
        }

        var command = CreateConvocatoriaCommandFromResourceAssembler.toCommandFromResource(orgId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Catálogo de convocatorias", description = "Consulta convocatorias disponibles con filtros opcionales por causa, distrito y estado.")
    public ResponseEntity<List<ConvocatoriaResource>> getConvocatorias(
            @RequestParam(required = false, name = "causa") String causa,
            @RequestParam(required = false, name = "causeType") String causeType,
            @RequestParam(required = false, name = "distrito") String distrito,
            @RequestParam(required = false, name = "district") String district,
            @RequestParam(required = false, name = "estado") String estado,
            @RequestParam(required = false, name = "status") String statusParam,
            @RequestParam(required = false, name = "duracion") String duracion
    ) {
        String filterCause = causa != null && !causa.isBlank() ? causa : causeType;
        String filterDistrict = distrito != null && !distrito.isBlank() ? distrito : district;
        String stateStr = estado != null && !estado.isBlank() ? estado : statusParam;

        EstadoConvocatoria filterStatus = null;
        if (stateStr != null && !stateStr.isBlank()) {
            try {
                filterStatus = EstadoConvocatoria.valueOf(stateStr.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                // If invalid status name, fallback to null or leave unconstrained
            }
        }

        var query = new GetFilteredConvocatoriasQuery(filterCause, filterDistrict, filterStatus);
        var convocatorias = queryService.handle(query);
        var resources = convocatorias.stream()
                .map(ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{convocatoriaId}")
    @Operation(summary = "Detalle de una convocatoria", description = "Obtiene los detalles completos de una convocatoria específica por su ID.")
    public ResponseEntity<ConvocatoriaResource> getConvocatoriaById(@PathVariable Long convocatoriaId) {
        var query = new GetConvocatoriaByIdQuery(convocatoriaId);
        var convocatoria = queryService.handle(query);
        return convocatoria
                .map(ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{convocatoriaId}")
    @Operation(summary = "Actualizar convocatoria en borrador", description = "Permite a la organización actualizar el contenido y datos de una convocatoria.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateConvocatoria(@PathVariable Long convocatoriaId,
                                                @Valid @RequestBody UpdateConvocatoriaResource resource) {
        var command = UpdateConvocatoriaCommandFromResourceAssembler.toCommandFromResource(convocatoriaId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{convocatoriaId}/publicar")
    @Operation(summary = "Publicar convocatoria", description = "Cambia el estado de una convocatoria de BORRADOR a PUBLICADA para que los estudiantes puedan postular.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> publishConvocatoria(@PathVariable Long convocatoriaId) {
        var command = new PublishConvocatoriaCommand(convocatoriaId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{convocatoriaId}/cerrar")
    @Operation(summary = "Cerrar convocatoria", description = "Cierra la convocatoria impidiendo nuevas postulaciones.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> closeConvocatoria(@PathVariable Long convocatoriaId) {
        var command = new CloseConvocatoriaCommand(convocatoriaId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Convocatorias por organización", description = "Lista todas las convocatorias pertenecientes a una organización / ONG específica.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<ConvocatoriaResource>> getConvocatoriasByOrganization(@PathVariable Long organizationId) {
        var query = new GetConvocatoriasByOrganizationIdQuery(organizationId);
        var convocatorias = queryService.handle(query);
        var resources = convocatorias.stream()
                .map(ConvocatoriaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
