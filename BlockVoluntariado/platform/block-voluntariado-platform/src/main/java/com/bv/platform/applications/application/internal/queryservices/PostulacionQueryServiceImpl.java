package com.bv.platform.applications.application.internal.queryservices;

import com.bv.platform.applications.application.queryservices.PostulacionQueryService;
import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.model.queries.GetPostulacionByIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByConvocatoriaIdQuery;
import com.bv.platform.applications.domain.model.queries.GetPostulacionesByVolunteerIdQuery;
import com.bv.platform.applications.domain.repositories.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulacionQueryServiceImpl implements PostulacionQueryService {

    private final PostulacionRepository repository;

    public PostulacionQueryServiceImpl(PostulacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Postulacion> handle(GetPostulacionByIdQuery query) {
        return repository.findById(query.postulacionId());
    }

    @Override
    public List<Postulacion> handle(GetPostulacionesByConvocatoriaIdQuery query) {
        return repository.findByConvocatoriaId(query.convocatoriaId());
    }

    @Override
    public List<Postulacion> handle(GetPostulacionesByVolunteerIdQuery query) {
        return repository.findByVolunteerId(query.volunteerId());
    }
}
