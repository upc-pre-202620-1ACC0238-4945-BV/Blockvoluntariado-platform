package com.bv.platform.applications.infrastructure.persistence.jpa.entities;

import com.bv.platform.applications.domain.model.valueobjects.EstadoPostulacion;
import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "postulaciones")
@Getter
@Setter
@NoArgsConstructor
public class PostulacionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "convocatoria_id", nullable = false)
    private Long convocatoriaId;

    @Column(name = "volunteer_id", nullable = false)
    private Long volunteerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EstadoPostulacion status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;
}
