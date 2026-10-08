package com.bv.platform.recognition.interfaces.rest;

import com.bv.platform.recognition.application.commandservices.CertificateCommandService;
import com.bv.platform.recognition.application.queryservices.CertificateQueryService;
import com.bv.platform.recognition.application.queryservices.GamificationQueryService;
import com.bv.platform.recognition.domain.model.commands.IssueCertificateCommand;
import com.bv.platform.recognition.domain.model.queries.GetCertificateByHashQuery;
import com.bv.platform.recognition.domain.model.queries.GetCertificatesByVolunteerIdQuery;
import com.bv.platform.recognition.interfaces.rest.resources.DigitalCertificateResource;
import com.bv.platform.recognition.interfaces.rest.resources.GamificationProfileResource;
import com.bv.platform.recognition.interfaces.rest.resources.IssueCertificateResource;
import com.bv.platform.recognition.interfaces.rest.resources.VolunteerHistoryItemResource;
import com.bv.platform.recognition.interfaces.rest.transform.DigitalCertificateResourceFromEntityAssembler;
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
@Tag(name = "Recognition and Certificates", description = "Certificados digitales verificables con SHA-256, gamificación e historial para CV")
public class CertificadosController {

    private final CertificateCommandService certificateCommandService;
    private final CertificateQueryService certificateQueryService;
    private final GamificationQueryService gamificationQueryService;

    public CertificadosController(CertificateCommandService certificateCommandService,
                                  CertificateQueryService certificateQueryService,
                                  GamificationQueryService gamificationQueryService) {
        this.certificateCommandService = certificateCommandService;
        this.certificateQueryService = certificateQueryService;
        this.gamificationQueryService = gamificationQueryService;
    }

    @PostMapping("/api/v1/volunteers/{volunteerId}/certificados")
    @Operation(summary = "Emitir certificado digital", description = "Genera un certificado digital firmado criptográficamente con hash SHA-256 para el voluntario.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> issueCertificate(@PathVariable Long volunteerId,
                                              @Valid @RequestBody IssueCertificateResource resource) {
        var command = new IssueCertificateCommand(
                volunteerId,
                resource.convocatoriaId(),
                resource.accreditedHours()
        );
        var result = certificateCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DigitalCertificateResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/api/v1/volunteers/{volunteerId}/certificados")
    @Operation(summary = "Listar certificados de un voluntario", description = "Obtiene todos los certificados digitales obtenidos por un estudiante universitario.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<DigitalCertificateResource>> getCertificatesByVolunteer(@PathVariable Long volunteerId) {
        var query = new GetCertificatesByVolunteerIdQuery(volunteerId);
        var certs = certificateQueryService.handle(query);
        var resources = certs.stream()
                .map(DigitalCertificateResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/api/v1/certificados/verificar/{verificationHash}")
    @Operation(summary = "Verificación pública de certificado", description = "Valida públicamente la autenticidad de un certificado digital empleando su hash SHA-256 único.")
    public ResponseEntity<DigitalCertificateResource> verifyCertificate(@PathVariable String verificationHash) {
        var query = new GetCertificateByHashQuery(verificationHash);
        var cert = certificateQueryService.handle(query);
        return cert
                .map(DigitalCertificateResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/v1/volunteers/{volunteerId}/logros")
    @Operation(summary = "Consultar logros y gamificación", description = "Consulta las horas acumuladas validadas, nivel alcanzado e insignias ganadas por el voluntario.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<GamificationProfileResource> getGamificationProfile(@PathVariable Long volunteerId) {
        var profile = gamificationQueryService.getGamificationProfile(volunteerId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/api/v1/volunteers/{volunteerId}/historial")
    @Operation(summary = "Historial verificado para CV", description = "Genera el reporte histórico estructurado de actividades concluidas para adjuntar al CV o portafolio.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<VolunteerHistoryItemResource>> getVolunteerHistory(@PathVariable Long volunteerId) {
        var history = gamificationQueryService.getVolunteerHistory(volunteerId);
        return ResponseEntity.ok(history);
    }
}
