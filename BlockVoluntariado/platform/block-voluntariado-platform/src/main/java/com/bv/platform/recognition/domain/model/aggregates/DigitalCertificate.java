package com.bv.platform.recognition.domain.model.aggregates;

import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Getter
public class DigitalCertificate extends AbstractDomainAggregateRoot<DigitalCertificate> {

    @Setter
    private Long id;
    private Long volunteerId;
    private Long convocatoriaId;
    private String verificationHash;
    private int accreditedHours;
    private String pdfDownloadUrl;
    private LocalDateTime issuedAt;

    public DigitalCertificate() {
        this.issuedAt = LocalDateTime.now();
    }

    public DigitalCertificate(Long id, Long volunteerId, Long convocatoriaId,
                              String verificationHash, int accreditedHours,
                              String pdfDownloadUrl, LocalDateTime issuedAt) {
        this.id = id;
        this.volunteerId = volunteerId;
        this.convocatoriaId = convocatoriaId;
        this.verificationHash = verificationHash;
        this.accreditedHours = accreditedHours;
        this.pdfDownloadUrl = pdfDownloadUrl;
        this.issuedAt = issuedAt != null ? issuedAt : LocalDateTime.now();
    }

    public DigitalCertificate(Long volunteerId, Long convocatoriaId, int accreditedHours) {
        this.volunteerId = volunteerId;
        this.convocatoriaId = convocatoriaId;
        this.accreditedHours = accreditedHours;
        this.issuedAt = LocalDateTime.now();
        this.verificationHash = generateHash(volunteerId, convocatoriaId, accreditedHours, this.issuedAt);
        this.pdfDownloadUrl = "/api/v1/certificados/" + this.verificationHash + "/download.pdf";
    }

    private static String generateHash(Long volunteerId, Long convocatoriaId, int hours, LocalDateTime issuedAt) {
        try {
            var raw = "BV-" + volunteerId + "-" + convocatoriaId + "-" + hours + "-" + issuedAt.toString();
            var digest = MessageDigest.getInstance("SHA-256");
            var hashBytes = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            var sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular hash SHA-256 para certificado", e);
        }
    }
}
