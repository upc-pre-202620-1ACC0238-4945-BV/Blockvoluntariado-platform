package com.bv.platform.volunteering.infrastructure.persistence.jpa.entities;

import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "convocatorias")
@Getter
@Setter
@NoArgsConstructor
public class ConvocatoriaPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cause_type", length = 80)
    private String causeType;

    @Column(name = "total_vacancies", nullable = false)
    private int totalVacancies;

    @Column(name = "occupied_vacancies", nullable = false)
    private int occupiedVacancies;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time", length = 20)
    private String startTime;

    @Column(name = "end_time", length = 20)
    private String endTime;

    @Column(name = "district", length = 100)
    private String district;

    @Column(name = "address_line", length = 255)
    private String addressLine;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EstadoConvocatoria status;
}
