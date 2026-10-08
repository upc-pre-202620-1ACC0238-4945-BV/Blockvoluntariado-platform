package com.bv.platform.recognition.application.queryservices;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.domain.model.queries.GetEvaluacionesByTargetIdQuery;

import java.util.List;

public interface EvaluacionQueryService {
    List<Evaluacion> handle(GetEvaluacionesByTargetIdQuery query);
}
