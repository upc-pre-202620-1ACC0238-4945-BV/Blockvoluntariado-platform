package com.bv.platform.recognition.application.commandservices;

import com.bv.platform.recognition.domain.model.aggregates.DigitalCertificate;
import com.bv.platform.recognition.domain.model.commands.IssueCertificateCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;

public interface CertificateCommandService {
    Result<DigitalCertificate, ApplicationError> handle(IssueCertificateCommand command);
}
