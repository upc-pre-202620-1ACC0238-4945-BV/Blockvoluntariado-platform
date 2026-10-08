package com.bv.platform.participation.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class AttendanceRecord {

    @Setter
    private Long id;
    private Long actividadId;
    private Long postulacionId;
    private Long volunteerId;
    private boolean isPresent;
    private int certifiedHours;
    private LocalDateTime checkInTime;
    private String supervisorNotes;

    public AttendanceRecord() {
    }

    public AttendanceRecord(Long id, Long actividadId, Long postulacionId, Long volunteerId,
                            boolean isPresent, int certifiedHours, LocalDateTime checkInTime, String supervisorNotes) {
        this.id = id;
        this.actividadId = actividadId;
        this.postulacionId = postulacionId;
        this.volunteerId = volunteerId;
        this.isPresent = isPresent;
        this.certifiedHours = certifiedHours;
        this.checkInTime = checkInTime;
        this.supervisorNotes = supervisorNotes;
    }

    public AttendanceRecord(Long actividadId, Long postulacionId, Long volunteerId,
                            boolean isPresent, int certifiedHours, String supervisorNotes) {
        this(null, actividadId, postulacionId, volunteerId, isPresent, certifiedHours,
                isPresent ? LocalDateTime.now() : null, supervisorNotes);
    }

    public void updateAttendance(boolean isPresent, int certifiedHours, String supervisorNotes) {
        this.isPresent = isPresent;
        this.certifiedHours = certifiedHours;
        this.supervisorNotes = supervisorNotes;
        if (isPresent && this.checkInTime == null) {
            this.checkInTime = LocalDateTime.now();
        } else if (!isPresent) {
            this.checkInTime = null;
        }
    }
}
