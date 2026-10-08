package com.bv.platform.volunteering.application.commandservices;

import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteering.domain.model.aggregates.Convocatoria;
import com.bv.platform.volunteering.domain.model.commands.CloseConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.CreateConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.PublishConvocatoriaCommand;
import com.bv.platform.volunteering.domain.model.commands.UpdateConvocatoriaCommand;

public interface ConvocatoriaCommandService {
    Result<Convocatoria, ApplicationError> handle(CreateConvocatoriaCommand command);
    Result<Convocatoria, ApplicationError> handle(UpdateConvocatoriaCommand command);
    Result<Convocatoria, ApplicationError> handle(PublishConvocatoriaCommand command);
    Result<Convocatoria, ApplicationError> handle(CloseConvocatoriaCommand command);
    Result<Convocatoria, ApplicationError> incrementOccupiedVacancies(Long convocatoriaId);
    Result<Convocatoria, ApplicationError> decrementOccupiedVacancies(Long convocatoriaId);
}
