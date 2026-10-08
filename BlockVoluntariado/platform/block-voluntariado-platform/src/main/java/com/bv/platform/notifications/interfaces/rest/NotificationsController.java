package com.bv.platform.notifications.interfaces.rest;

import com.bv.platform.notifications.application.commandservices.NotificationCommandService;
import com.bv.platform.notifications.application.queryservices.NotificationQueryService;
import com.bv.platform.notifications.domain.model.commands.MarkAllNotificationsAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.bv.platform.notifications.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.bv.platform.notifications.domain.model.queries.GetNotificationPreferencesQuery;
import com.bv.platform.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.bv.platform.notifications.interfaces.rest.resources.NotificationPreferencesResource;
import com.bv.platform.notifications.interfaces.rest.resources.NotificationResource;
import com.bv.platform.notifications.interfaces.rest.resources.SendNotificationResource;
import com.bv.platform.notifications.interfaces.rest.transform.NotificationPreferencesResourceFromEntityAssembler;
import com.bv.platform.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.bv.platform.notifications.interfaces.rest.transform.SendNotificationCommandFromResourceAssembler;
import com.bv.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Communication and Notifications", description = "Bandeja de notificaciones in-app y configuración de preferencias")
@SecurityRequirement(name = "bearerAuth")
public class NotificationsController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationsController(NotificationCommandService commandService,
                                   NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping("/api/v1/usuarios/{userId}/notificaciones")
    @Operation(summary = "Bandeja de notificaciones del usuario", description = "Recupera todas las alertas y notificaciones in-app recibidas por el usuario.")
    public ResponseEntity<List<NotificationResource>> getUserNotifications(@PathVariable Long userId) {
        var query = new GetUserNotificationsQuery(userId);
        var notifications = queryService.handle(query);
        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PatchMapping("/api/v1/notificaciones/{notificacionId}/leer")
    @Operation(summary = "Marcar notificación como leída", description = "Cambia el estado de una notificación específica a leída.")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificacionId) {
        var command = new MarkNotificationAsReadCommand(notificacionId);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/api/v1/usuarios/{userId}/notificaciones/leer-todas")
    @Operation(summary = "Marcar todas las notificaciones como leídas", description = "Marca masivamente todas las alertas no leídas del usuario.")
    public ResponseEntity<List<NotificationResource>> markAllAsRead(@PathVariable Long userId) {
        var command = new MarkAllNotificationsAsReadCommand(userId);
        var result = commandService.handle(command);
        if (result instanceof com.bv.platform.shared.application.result.Result.Success(var notifs)) {
            var resources = notifs.stream()
                    .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(resources);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/api/v1/usuarios/{userId}/preferencias-notificaciones")
    @Operation(summary = "Consultar preferencias de notificación", description = "Obtiene la configuración actual de canales de notificación (Email, Push, Alertas de causas).")
    public ResponseEntity<NotificationPreferencesResource> getPreferences(@PathVariable Long userId) {
        var query = new GetNotificationPreferencesQuery(userId);
        var pref = queryService.handle(query);
        return ResponseEntity.ok(NotificationPreferencesResourceFromEntityAssembler.toResourceFromEntity(pref));
    }

    @PutMapping("/api/v1/usuarios/{userId}/preferencias-notificaciones")
    @Operation(summary = "Actualizar preferencias de notificación", description = "Configura los canales y tipos de notificaciones que el usuario desea recibir.")
    public ResponseEntity<?> updatePreferences(@PathVariable Long userId,
                                               @RequestBody NotificationPreferencesResource resource) {
        var command = new UpdateNotificationPreferencesCommand(
                userId,
                resource.emailEnabled(),
                resource.pushEnabled(),
                resource.causeAlerts()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationPreferencesResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/api/v1/notificaciones/enviar")
    @Operation(summary = "Despachar notificación", description = "Envía una nueva notificación in-app al buzón del usuario.")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody SendNotificationResource resource) {
        var command = SendNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
