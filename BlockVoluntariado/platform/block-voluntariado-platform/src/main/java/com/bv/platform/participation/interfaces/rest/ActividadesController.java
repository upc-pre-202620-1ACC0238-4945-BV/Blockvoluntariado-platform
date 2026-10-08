package com.bv.platform.participation.interfaces.rest;

import com.bv.platform.participation.application.commandservices.ParticipationCommandService;
import com.bv.platform.participation.application.queryservices.ParticipationQueryService;
import com.bv.platform.participation.domain.model.commands.AttendanceItem;
import com.bv.platform.participation.domain.model.commands.CompleteActividadCommand;
import com.bv.platform.participation.domain.model.commands.RecordBulkAttendanceCommand;
import com.bv.platform.participation.domain.model.commands.StartActividadCommand;
import com.bv.platform.participation.domain.model.queries.GetActividadByConvocatoriaIdQuery;
import com.bv.platform.participation.domain.model.queries.GetActividadByIdQuery;
import com.bv.platform.participation.domain.model.queries.GetAttendanceListByActividadIdQuery;
import com.bv.platform.participation.interfaces.rest.resources.ActividadResource;
import com.bv.platform.participation.interfaces.rest.resources.AttendanceRecordResource;
import com.bv.platform.participation.interfaces.rest.resources.CreateActividadResource;
import com.bv.platform.participation.interfaces.rest.resources.RecordBulkAttendanceResource;
import com.bv.platform.participation.interfaces.rest.transform.ActividadResourceFromEntityAssembler;
import com.bv.platform.participation.interfaces.rest.transform.AttendanceRecordResourceFromEntityAssembler;
import com.bv.platform.participation.interfaces.rest.transform.CreateActividadCommandFromResourceAssembler;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/actividades", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Participation Management", description = "Ejecución operativa en campo y control de asistencia de voluntarios")
@SecurityRequirement(name = "bearerAuth")
public class ActividadesController {

    private final ParticipationCommandService commandService;
    private final ParticipationQueryService queryService;

    public ActividadesController(ParticipationCommandService commandService,
                                 ParticipationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Crear o inicializar actividad de voluntariado", description = "Crea una actividad asociada a una convocatoria e incorpora a los postulantes aceptados a la lista de asistencia.")
    public ResponseEntity<?> createActividad(@Valid @RequestBody CreateActividadResource resource) {
        var command = CreateActividadCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ActividadResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{actividadId}")
    @Operation(summary = "Detalle de actividad", description = "Obtiene los detalles operativos y la lista de participantes de una actividad.")
    public ResponseEntity<ActividadResource> getActividadById(@PathVariable Long actividadId) {
        var query = new GetActividadByIdQuery(actividadId);
        var actividad = queryService.handle(query);
        return actividad
                .map(ActividadResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/convocatoria/{convocatoriaId}")
    @Operation(summary = "Actividad por convocatoria", description = "Obtiene la actividad operativa ligada a una convocatoria específica.")
    public ResponseEntity<ActividadResource> getActividadByConvocatoria(@PathVariable Long convocatoriaId) {
        var query = new GetActividadByConvocatoriaIdQuery(convocatoriaId);
        var actividad = queryService.handle(query);
        return actividad
                .map(ActividadResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{actividadId}/participantes")
    @Operation(summary = "Participantes esperados en campo", description = "Lista los estudiantes convocados y el estado actual de su asistencia.")
    public ResponseEntity<List<AttendanceRecordResource>> getParticipantes(@PathVariable Long actividadId) {
        var query = new GetAttendanceListByActividadIdQuery(actividadId);
        var records = queryService.handle(query);
        var resources = records.stream()
                .map(AttendanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{actividadId}/asistencias")
    @Operation(summary = "Registrar asistencias en campo", description = "Registra individual o masivamente la asistencia, horas efectivas y notas del supervisor.")
    public ResponseEntity<?> recordAttendances(@PathVariable Long actividadId,
                                               @RequestBody RecordBulkAttendanceResource resource) {
        List<AttendanceItem> items = new ArrayList<>();
        if (resource != null && resource.attendances() != null) {
            for (var a : resource.attendances()) {
                items.add(new AttendanceItem(
                        a.postulacionId(),
                        a.volunteerId(),
                        Boolean.TRUE.equals(a.isPresent()),
                        a.certifiedHours() != null ? a.certifiedHours() : 0,
                        a.supervisorNotes()
                ));
            }
        }

        var command = new RecordBulkAttendanceCommand(actividadId, items);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ActividadResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{actividadId}/iniciar")
    @Operation(summary = "Iniciar actividad", description = "Marca la jornada de voluntariado como EN_CURSO.")
    public ResponseEntity<?> startActividad(@PathVariable Long actividadId) {
        var command = new StartActividadCommand(actividadId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ActividadResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{actividadId}/finalizar")
    @Operation(summary = "Finalizar actividad", description = "Marca la actividad como COMPLETADA y acredita las horas sociales acumuladas a los voluntarios presentes.")
    public ResponseEntity<?> completeActividad(@PathVariable Long actividadId) {
        var command = new CompleteActividadCommand(actividadId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ActividadResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
