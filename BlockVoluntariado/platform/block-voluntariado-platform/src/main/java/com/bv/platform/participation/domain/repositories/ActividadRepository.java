package com.bv.platform.participation.domain.repositories;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;

import java.util.List;
import java.util.Optional;

public interface ActividadRepository {
    List<ActividadVoluntariado> findAll();
    Optional<ActividadVoluntariado> findById(Long id);
    Optional<ActividadVoluntariado> findByConvocatoriaId(Long convocatoriaId);
    ActividadVoluntariado save(ActividadVoluntariado actividad);
    boolean existsById(Long id);
    boolean existsByConvocatoriaId(Long convocatoriaId);
}
