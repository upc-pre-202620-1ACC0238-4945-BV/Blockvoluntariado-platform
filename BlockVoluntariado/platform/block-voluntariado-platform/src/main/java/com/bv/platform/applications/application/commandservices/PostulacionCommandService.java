package com.bv.platform.applications.application.commandservices;

import com.bv.platform.applications.domain.model.aggregates.Postulacion;
import com.bv.platform.applications.domain.model.commands.AcceptPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.CancelPostulacionCommand;
import com.bv.platform.applications.domain.model.commands.CreatePostulacionCommand;
import com.bv.platform.applications.domain.model.commands.RejectPostulacionCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;

public interface PostulacionCommandService {
    Result<Postulacion, ApplicationError> handle(CreatePostulacionCommand command);
    Result<Postulacion, ApplicationError> handle(AcceptPostulacionCommand command);
    Result<Postulacion, ApplicationError> handle(RejectPostulacionCommand command);
    Result<Postulacion, ApplicationError> handle(CancelPostulacionCommand command);
}
