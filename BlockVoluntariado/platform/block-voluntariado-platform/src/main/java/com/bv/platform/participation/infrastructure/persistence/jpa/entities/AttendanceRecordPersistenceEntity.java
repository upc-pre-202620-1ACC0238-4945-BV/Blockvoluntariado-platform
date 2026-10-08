package com.bv.platform.participation.infrastructure.persistence.jpa.entities;

import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
@Getter
@Setter
@NoArgsConstructor
public class AttendanceRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadPersistenceEntity actividad;

    @Column(name = "postulacion_id")
    private Long postulacionId;

    @Column(name = "volunteer_id", nullable = false)
    private Long volunteerId;

    @Column(name = "is_present", nullable = false)
    private boolean isPresent;

    @Column(name = "certified_hours", nullable = false)
    private int certifiedHours;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "supervisor_notes")
    private String supervisorNotes;
}
