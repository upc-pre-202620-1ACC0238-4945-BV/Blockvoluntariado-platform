package com.bv.platform.iam.application.commandservices;

import com.bv.platform.iam.domain.model.aggregates.User;
import com.bv.platform.iam.domain.model.commands.PasswordRecoveryCommand;
import com.bv.platform.iam.domain.model.commands.SignInCommand;
import com.bv.platform.iam.domain.model.commands.SignUpCommand;
import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import org.springframework.data.util.Pair;

public interface UserCommandService {
    Result<Pair<User, String>, ApplicationError> handle(SignInCommand command);
    Result<User, ApplicationError> handle(SignUpCommand command);
    Result<String, ApplicationError> handle(PasswordRecoveryCommand command);
}
