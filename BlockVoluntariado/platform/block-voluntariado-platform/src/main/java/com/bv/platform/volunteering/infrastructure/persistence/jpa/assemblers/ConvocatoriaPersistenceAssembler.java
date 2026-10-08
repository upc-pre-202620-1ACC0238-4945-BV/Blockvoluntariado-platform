package com.bv.platform.volunteering.infrastructure.persistence.jpa.assemblers;

import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.valueobjects.Horario;
import com.bv.platform.volunteering.domain.model.valueobjects.Ubicacion;
import com.bv.platform.volunteering.infrastructure.persistence.jpa.entities.ConvocatoriaPersistenceEntity;

public final class ConvocatoriaPersistenceAssembler {

    private ConvocatoriaPersistenceAssembler() {
    }

    public static Convocatoria toDomainFromPersistence(ConvocatoriaPersistenceEntity entity) {
        if (entity == null) return null;

        var horario = new Horario(
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStartTime(),
                entity.getEndTime()
        );

        var ubicacion = new Ubicacion(
                entity.getDistrict(),
                entity.getAddressLine(),
                entity.getLatitude(),
                entity.getLongitude()
        );

        return new Convocatoria(
                entity.getId(),
                entity.getOrganizationId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCauseType(),
                entity.getTotalVacancies(),
                entity.getOccupiedVacancies(),
                horario,
                ubicacion,
                entity.getStatus()
        );
    }

    public static ConvocatoriaPersistenceEntity toPersistenceFromDomain(Convocatoria domain) {
        if (domain == null) return null;

        var entity = new ConvocatoriaPersistenceEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setOrganizationId(domain.getOrganizationId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setCauseType(domain.getCauseType());
        entity.setTotalVacancies(domain.getTotalVacancies());
        entity.setOccupiedVacancies(domain.getOccupiedVacancies());
        entity.setStatus(domain.getStatus());

        if (domain.getHorario() != null) {
            entity.setStartDate(domain.getHorario().startDate());
            entity.setEndDate(domain.getHorario().endDate());
            entity.setStartTime(domain.getHorario().startTime());
            entity.setEndTime(domain.getHorario().endTime());
        }

        if (domain.getUbicacion() != null) {
            entity.setDistrict(domain.getUbicacion().district());
            entity.setAddressLine(domain.getUbicacion().addressLine());
            entity.setLatitude(domain.getUbicacion().latitude());
            entity.setLongitude(domain.getUbicacion().longitude());
        }

        return entity;
    }
}
