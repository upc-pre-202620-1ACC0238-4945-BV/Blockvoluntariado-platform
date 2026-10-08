package com.bv.platform.volunteering.domain.model.queries;

import com.bv.platform.volunteering.domain.model.valueobjects.EstadoConvocatoria;

public record GetFilteredConvocatoriasQuery(
        String causeType,
        String district,
        EstadoConvocatoria status
) {}
