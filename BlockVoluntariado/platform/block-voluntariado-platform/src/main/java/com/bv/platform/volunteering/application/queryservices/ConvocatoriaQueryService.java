package com.bv.platform.volunteering.application.queryservices;

import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriaByIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriasByOrganizationIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetFilteredConvocatoriasQuery;

import java.util.List;
import java.util.Optional;

public interface ConvocatoriaQueryService {
    Optional<Convocatoria> handle(GetConvocatoriaByIdQuery query);
    List<Convocatoria> handle(GetFilteredConvocatoriasQuery query);
    List<Convocatoria> handle(GetConvocatoriasByOrganizationIdQuery query);
}
