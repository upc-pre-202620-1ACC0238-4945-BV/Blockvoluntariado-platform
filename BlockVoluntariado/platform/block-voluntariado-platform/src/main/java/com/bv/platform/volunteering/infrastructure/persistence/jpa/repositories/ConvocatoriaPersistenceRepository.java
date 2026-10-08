package com.bv.platform.volunteering.infrastructure.persistence.jpa.repositories;

import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;
import com.bv.platform.volunteering.infrastructure.persistence.jpa.entities.ConvocatoriaPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConvocatoriaPersistenceRepository extends JpaRepository<ConvocatoriaPersistenceEntity, Long> {

    List<ConvocatoriaPersistenceEntity> findByOrganizationId(Long organizationId);

    @Query("SELECT c FROM ConvocatoriaPersistenceEntity c WHERE " +
            "(:causeType IS NULL OR :causeType = '' OR LOWER(c.causeType) LIKE LOWER(CONCAT('%', :causeType, '%'))) AND " +
            "(:district IS NULL OR :district = '' OR LOWER(c.district) LIKE LOWER(CONCAT('%', :district, '%'))) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<ConvocatoriaPersistenceEntity> findFiltered(
            @Param("causeType") String causeType,
            @Param("district") String district,
            @Param("status") EstadoConvocatoria status
    );
}
