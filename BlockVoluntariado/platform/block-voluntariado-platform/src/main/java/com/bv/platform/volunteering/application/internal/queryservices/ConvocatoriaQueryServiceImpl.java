package com.bv.platform.volunteering.application.internal.queryservices;

import com.bv.platform.volunteering.application.queryservices.ConvocatoriaQueryService;
import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriaByIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetConvocatoriasByOrganizationIdQuery;
import com.bv.platform.volunteering.domain.model.queries.GetFilteredConvocatoriasQuery;
import com.bv.platform.volunteering.domain.repositories.ConvocatoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConvocatoriaQueryServiceImpl implements ConvocatoriaQueryService {

    private final ConvocatoriaRepository repository;

    public ConvocatoriaQueryServiceImpl(ConvocatoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Convocatoria> handle(GetConvocatoriaByIdQuery query) {
        return repository.findById(query.convocatoriaId());
    }

    @Override
    public List<Convocatoria> handle(GetFilteredConvocatoriasQuery query) {
        return repository.findFiltered(query.causeType(), query.district(), query.status());
    }

    @Override
    public List<Convocatoria> handle(GetConvocatoriasByOrganizationIdQuery query) {
        return repository.findByOrganizationId(query.organizationId());
    }
}
