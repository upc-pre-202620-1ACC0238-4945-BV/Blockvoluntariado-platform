package com.bv.platform.applications.application.queryservices;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.model.queries.GetPostulacionByIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByConvocatoriaIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByVolunteerIdQuery;

import java.util.List;
import java.util.Optional;

public interface PostulacionQueryService {
    Optional<Postulacion> handle(GetPostulacionByIdQuery query);
    List<Postulacion> handle(GetPostulacionesByConvocatoriaIdQuery query);
    List<Postulacion> handle(GetPostulacionesByVolunteerIdQuery query);
}
