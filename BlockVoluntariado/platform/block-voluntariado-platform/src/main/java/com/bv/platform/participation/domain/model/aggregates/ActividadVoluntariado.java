package com.bv.platform.participation.domain.model.aggregates;

import com.bv.platform.participation.domain.model.entities.AttendanceRecord;
import com.bv.platform.participation.domain.model.valueobjects.EstadoActividad;
import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ActividadVoluntariado extends AbstractDomainAggregateRoot<ActividadVoluntariado> {

    @Setter
    private Long id;
    private Long convocatoriaId;
    private String titulo;
    private EstadoActividad status;
    private LocalDate fechaActividad;
    private List<AttendanceRecord> asistencias;

    public ActividadVoluntariado() {
        this.status = EstadoActividad.PLANIFICADA;
        this.asistencias = new ArrayList<>();
    }

    public ActividadVoluntariado(Long id, Long convocatoriaId, String titulo,
                                EstadoActividad status, LocalDate fechaActividad,
                                List<AttendanceRecord> asistencias) {
        this.id = id;
        this.convocatoriaId = convocatoriaId;
        this.titulo = titulo;
        this.status = status != null ? status : EstadoActividad.PLANIFICADA;
        this.fechaActividad = fechaActividad != null ? fechaActividad : LocalDate.now();
        this.asistencias = asistencias != null ? new ArrayList<>(asistencias) : new ArrayList<>();
    }

    public ActividadVoluntariado(Long convocatoriaId, String titulo, LocalDate fechaActividad) {
        this(null, convocatoriaId, titulo, EstadoActividad.PLANIFICADA, fechaActividad, new ArrayList<>());
    }

    public void start() {
        if (this.status == EstadoActividad.COMPLETADA) {
            throw new IllegalStateException("No se puede iniciar una actividad ya completada.");
        }
        if (this.status == EstadoActividad.CANCELADA) {
            throw new IllegalStateException("No se puede iniciar una actividad cancelada.");
        }
        this.status = EstadoActividad.EN_CURSO;
    }

    public void complete() {
        if (this.status == EstadoActividad.CANCELADA) {
            throw new IllegalStateException("No se puede completar una actividad cancelada.");
        }
        this.status = EstadoActividad.COMPLETADA;
    }

    public void cancel() {
        if (this.status == EstadoActividad.COMPLETADA) {
            throw new IllegalStateException("No se puede cancelar una actividad completada.");
        }
        this.status = EstadoActividad.CANCELADA;
    }

    public void recordOrUpdateAttendance(Long postulacionId, Long volunteerId,
                                         boolean isPresent, int certifiedHours, String supervisorNotes) {
        Optional<AttendanceRecord> existing = this.asistencias.stream()
                .filter(a -> a.getVolunteerId().equals(volunteerId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().updateAttendance(isPresent, certifiedHours, supervisorNotes);
        } else {
            var newRecord = new AttendanceRecord(
                    this.id,
                    postulacionId,
                    volunteerId,
                    isPresent,
                    certifiedHours,
                    supervisorNotes
            );
            this.asistencias.add(newRecord);
        }
    }

    public boolean isCompleted() {
        return this.status == EstadoActividad.COMPLETADA;
    }
}
