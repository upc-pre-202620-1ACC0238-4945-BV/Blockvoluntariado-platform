package com.bv.platform.participation.infrastructure.persistence.jpa.entities;

import com.bv.platform.participation.domain.model.valueobjects.EstadoActividad;
import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actividades")
@Getter
@Setter
@NoArgsConstructor
public class ActividadPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "convocatoria_id", nullable = false)
    private Long convocatoriaId;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EstadoActividad status;

    @Column(name = "fecha_actividad")
    private LocalDate fechaActividad;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AttendanceRecordPersistenceEntity> asistencias = new ArrayList<>();
}
