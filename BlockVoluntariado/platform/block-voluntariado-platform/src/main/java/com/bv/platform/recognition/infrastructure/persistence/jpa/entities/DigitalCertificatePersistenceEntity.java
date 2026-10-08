package com.bv.platform.recognition.infrastructure.persistence.jpa.entities;

import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificados_digitales")
@Getter
@Setter
@NoArgsConstructor
public class DigitalCertificatePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "volunteer_id", nullable = false)
    private Long volunteerId;

    @Column(name = "convocatoria_id", nullable = false)
    private Long convocatoriaId;

    @Column(name = "verification_hash", nullable = false, unique = true, length = 64)
    private String verificationHash;

    @Column(name = "accredited_hours", nullable = false)
    private int accreditedHours;

    @Column(name = "pdf_download_url")
    private String pdfDownloadUrl;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;
}
