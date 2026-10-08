package com.bv.platform.applications.domain.model.aggregates;

import com.bv.platform.applications.domain.model.valueobjects.EstadoPostulacion;
import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Postulacion extends AbstractDomainAggregateRoot<Postulacion> {

    @Setter
    private Long id;
    private Long convocatoriaId;
    private Long volunteerId;
    private EstadoPostulacion status;
    private String rejectionReason;
    private LocalDateTime appliedAt;

    public Postulacion() {
        this.status = EstadoPostulacion.PENDIENTE;
        this.appliedAt = LocalDateTime.now();
    }

    public Postulacion(Long id, Long convocatoriaId, Long volunteerId, EstadoPostulacion status,
                       String rejectionReason, LocalDateTime appliedAt) {
        this.id = id;
        this.convocatoriaId = convocatoriaId;
        this.volunteerId = volunteerId;
        this.status = status != null ? status : EstadoPostulacion.PENDIENTE;
        this.rejectionReason = rejectionReason;
        this.appliedAt = appliedAt != null ? appliedAt : LocalDateTime.now();
    }

    public Postulacion(Long convocatoriaId, Long volunteerId) {
        this(null, convocatoriaId, volunteerId, EstadoPostulacion.PENDIENTE, null, LocalDateTime.now());
    }

    public void accept() {
        if (this.status == EstadoPostulacion.ACEPTADA) {
            throw new IllegalStateException("La postulación ya se encuentra aceptada.");
        }
        if (this.status == EstadoPostulacion.RECHAZADA) {
            throw new IllegalStateException("No se puede aceptar una postulación previamente rechazada.");
        }
        if (this.status == EstadoPostulacion.CANCELADA) {
            throw new IllegalStateException("No se puede aceptar una postulación cancelada.");
        }
        this.status = EstadoPostulacion.ACEPTADA;
    }

    public void reject(String reason) {
        if (this.status == EstadoPostulacion.CANCELADA) {
            throw new IllegalStateException("No se puede rechazar una postulación cancelada.");
        }
        this.status = EstadoPostulacion.RECHAZADA;
        this.rejectionReason = reason;
    }

    public void cancel() {
        if (this.status == EstadoPostulacion.RECHAZADA) {
            throw new IllegalStateException("No se puede cancelar una postulación rechazada.");
        }
        this.status = EstadoPostulacion.CANCELADA;
    }

    public boolean isAccepted() {
        return this.status == EstadoPostulacion.ACEPTADA;
    }

    public boolean isPending() {
        return this.status == EstadoPostulacion.PENDIENTE;
    }
}
