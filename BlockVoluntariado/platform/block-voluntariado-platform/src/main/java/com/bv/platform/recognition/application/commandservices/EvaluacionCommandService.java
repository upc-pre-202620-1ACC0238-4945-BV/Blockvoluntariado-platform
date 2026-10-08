package com.bv.platform.recognition.application.commandservices;

import com.bv.platform.recognition.domain.model.aggregates.Evaluacion;
import com.bv.platform.recognition.domain.model.commands.CreateEvaluacionCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;

public interface EvaluacionCommandService {
    Result<Evaluacion, ApplicationError> handle(CreateEvaluacionCommand command);
}
