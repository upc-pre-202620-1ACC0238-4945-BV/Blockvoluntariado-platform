package com.bv.platform.recognition.interfaces.rest;

import com.bv.platform.iam.interfaces.acl.IamContextFacade;
import com.bv.platform.recognition.application.commandservices.EvaluacionCommandService;
import com.bv.platform.recognition.application.queryservices.EvaluacionQueryService;
import com.bv.platform.recognition.domain.model.queries.GetEvaluacionesByTargetIdQuery;
import com.bv.platform.recognition.domain.model.valueobjects.TipoEvaluador;
import com.bv.platform.recognition.interfaces.rest.resources.CreateEvaluacionResource;
import com.bv.platform.recognition.interfaces.rest.resources.EvaluacionResource;
import com.bv.platform.recognition.interfaces.rest.transform.CreateEvaluacionCommandFromResourceAssembler;
import com.bv.platform.recognition.interfaces.rest.transform.EvaluacionResourceFromEntityAssembler;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bv.platform.volunteers.interfaces.acl.VolunteersContextFacade;
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
@RequestMapping(value = "/api/v1/evaluaciones", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reviews and Feedback", description = "Evaluación bidireccional entre organizaciones sociales y voluntarios")
@SecurityRequirement(name = "bearerAuth")
public class EvaluacionesController {

    private final EvaluacionCommandService commandService;
    private final EvaluacionQueryService queryService;
    private final IamContextFacade iamContextFacade;
    private final VolunteersContextFacade volunteersContextFacade;

    public EvaluacionesController(EvaluacionCommandService commandService,
                                  EvaluacionQueryService queryService,
                                  IamContextFacade iamContextFacade,
                                  VolunteersContextFacade volunteersContextFacade) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.iamContextFacade = iamContextFacade;
        this.volunteersContextFacade = volunteersContextFacade;
    }

    @PostMapping("/voluntarios/{volunteerId}")
    @Operation(summary = "ONG evalúa a voluntario", description = "La organización social califica de 1 a 5 estrellas el desempeño y compromiso del voluntario.")
    public ResponseEntity<?> reviewVolunteer(@PathVariable Long volunteerId,
                                             @Valid @RequestBody CreateEvaluacionResource resource,
                                             Authentication authentication) {
        Long orgId = null;
        if (resource.evaluadorId() != null && resource.evaluadorId() > 0) {
            orgId = resource.evaluadorId();
        } else if (authentication != null && authentication.isAuthenticated()) {
            orgId = iamContextFacade.fetchUserIdByUsername(authentication.getName());
        }

        var command = CreateEvaluacionCommandFromResourceAssembler.toCommandFromResource(
                orgId,
                volunteerId,
                TipoEvaluador.ONG,
                resource
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EvaluacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/ong/{ongId}")
    @Operation(summary = "Voluntario evalúa a la ONG", description = "El estudiante califica de 1 a 5 estrellas y redacta una reseña sobre la organización anfitriona.")
    public ResponseEntity<?> reviewOrganization(@PathVariable Long ongId,
                                                @Valid @RequestBody CreateEvaluacionResource resource,
                                                Authentication authentication) {
        Long volunteerId = null;
        if (resource.evaluadorId() != null && resource.evaluadorId() > 0) {
            volunteerId = resource.evaluadorId();
        } else if (authentication != null && authentication.isAuthenticated()) {
            var userId = iamContextFacade.fetchUserIdByUsername(authentication.getName());
            if (userId != null && userId > 0) {
                volunteerId = volunteersContextFacade.getVolunteerIdByUserId(userId);
            }
        }

        var command = CreateEvaluacionCommandFromResourceAssembler.toCommandFromResource(
                volunteerId,
                ongId,
                TipoEvaluador.VOLUNTARIO,
                resource
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EvaluacionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/target/{targetId}")
    @Operation(summary = "Consultar evaluaciones recibidas", description = "Lista todas las evaluaciones y comentarios recibidos por un voluntario o por una organización.")
    public ResponseEntity<List<EvaluacionResource>> getReviewsByTarget(@PathVariable Long targetId) {
        var query = new GetEvaluacionesByTargetIdQuery(targetId);
        var reviews = queryService.handle(query);
        var resources = reviews.stream()
                .map(EvaluacionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
