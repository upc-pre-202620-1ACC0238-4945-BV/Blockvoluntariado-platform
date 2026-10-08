package com.bv.platform.participation.application.commandservices;

import com.bv.platform.participation.domain.model.aggregates.ActividadVoluntariado;
import com.bv.platform.participation.domain.model.commands.CreateActividadCommand;
import com.bv.platform.participation.domain.model.commands.CompleteActividadCommand;
import com.bv.platform.participation.domain.model.commands.RecordAttendanceCommand;
import com.bv.platform.participation.domain.model.commands.RecordBulkAttendanceCommand;
import com.bv.platform.participation.domain.model.commands.StartActividadCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;

public interface ParticipationCommandService {
    Result<ActividadVoluntariado, ApplicationError> handle(CreateActividadCommand command);
    Result<ActividadVoluntariado, ApplicationError> handle(StartActividadCommand command);
    Result<ActividadVoluntariado, ApplicationError> handle(CompleteActividadCommand command);
    Result<ActividadVoluntariado, ApplicationError> handle(RecordAttendanceCommand command);
    Result<ActividadVoluntariado, ApplicationError> handle(RecordBulkAttendanceCommand command);
}
