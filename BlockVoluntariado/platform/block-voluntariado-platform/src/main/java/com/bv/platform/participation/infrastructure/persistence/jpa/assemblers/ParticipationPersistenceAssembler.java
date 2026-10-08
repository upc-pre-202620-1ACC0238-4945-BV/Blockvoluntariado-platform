package com.bv.platform.participation.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.entities.AttendanceRecord;
import com.bv.platform.participation.infrastructure.persistence.jpa.entities.ActividadPersistenceEntity;
import com.bv.platform.participation.infrastructure.persistence.jpa.entities.AttendanceRecordPersistenceEntity;

import java.util.ArrayList;
import java.util.List;

public final class ParticipationPersistenceAssembler {

    private ParticipationPersistenceAssembler() {
    }

    public static ActividadVoluntariado toDomainFromPersistence(ActividadPersistenceEntity entity) {
        if (entity == null) return null;

        List<AttendanceRecord> asistencias = new ArrayList<>();
        if (entity.getAsistencias() != null) {
            for (var a : entity.getAsistencias()) {
                asistencias.add(new AttendanceRecord(
                        a.getId(),
                        entity.getId(),
                        a.getPostulacionId(),
                        a.getVolunteerId(),
                        a.isPresent(),
                        a.getCertifiedHours(),
                        a.getCheckInTime(),
                        a.getSupervisorNotes()
                ));
            }
        }

        return new ActividadVoluntariado(
                entity.getId(),
                entity.getConvocatoriaId(),
                entity.getTitulo(),
                entity.getStatus(),
                entity.getFechaActividad(),
                asistencias
        );
    }

    public static ActividadPersistenceEntity toPersistenceFromDomain(ActividadVoluntariado domain) {
        if (domain == null) return null;

        var entity = new ActividadPersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setConvocatoriaId(domain.getConvocatoriaId());
        entity.setTitulo(domain.getTitulo());
        entity.setStatus(domain.getStatus());
        entity.setFechaActividad(domain.getFechaActividad());

        List<AttendanceRecordPersistenceEntity> persistenceRecords = new ArrayList<>();
        if (domain.getAsistencias() != null) {
            for (var a : domain.getAsistencias()) {
                var recordEntity = new AttendanceRecordPersistenceEntity();
                if (a.getId() != null) {
                    recordEntity.setId(a.getId());
                }
                recordEntity.setActividad(entity);
                recordEntity.setPostulacionId(a.getPostulacionId());
                recordEntity.setVolunteerId(a.getVolunteerId());
                recordEntity.setPresent(a.isPresent());
                recordEntity.setCertifiedHours(a.getCertifiedHours());
                recordEntity.setCheckInTime(a.getCheckInTime());
                recordEntity.setSupervisorNotes(a.getSupervisorNotes());
                persistenceRecords.add(recordEntity);
            }
        }
        entity.setAsistencias(persistenceRecords);

        return entity;
    }
}
