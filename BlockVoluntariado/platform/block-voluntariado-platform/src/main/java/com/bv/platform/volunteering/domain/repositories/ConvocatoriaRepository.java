package com.bv.platform.volunteering.domain.repositories;

import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;

import java.util.List;
import java.util.Optional;

public interface ConvocatoriaRepository {
    List<Convocatoria> findAll();
    Optional<Convocatoria> findById(Long id);
    List<Convocatoria> findByOrganizationId(Long organizationId);
    List<Convocatoria> findFiltered(String causeType, String district, EstadoConvocatoria status);
    Convocatoria save(Convocatoria convocatoria);
    boolean existsById(Long id);
}
