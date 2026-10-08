package com.bv.platform.applications.domain.repositories;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;

import java.util.List;
import java.util.Optional;

public interface PostulacionRepository {
    List<Postulacion> findAll();
    Optional<Postulacion> findById(Long id);
    List<Postulacion> findByConvocatoriaId(Long convocatoriaId);
    List<Postulacion> findByVolunteerId(Long volunteerId);
    Optional<Postulacion> findByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId);
    boolean existsByConvocatoriaIdAndVolunteerId(Long convocatoriaId, Long volunteerId);
    Postulacion save(Postulacion postulacion);
    boolean existsById(Long id);
}
