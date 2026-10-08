package com.bv.platform.recognition.application.internal.queryservices;

import com.bv.platform.recognition.application.queryservices.EvaluacionQueryService;
import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.domain.model.queries.GetEvaluacionesByTargetIdQuery;
import com.bv.platform.recognition.domain.repositories.EvaluacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionQueryServiceImpl implements EvaluacionQueryService {

    private final EvaluacionRepository repository;

    public EvaluacionQueryServiceImpl(EvaluacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Evaluacion> handle(GetEvaluacionesByTargetIdQuery query) {
        return repository.findByEvaluadoId(query.targetId());
    }
}
