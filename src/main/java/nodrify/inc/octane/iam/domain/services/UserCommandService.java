package nodrify.inc.octane.iam.domain.services;

import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.domain.model.commands.DeleteUserCommand;
import nodrify.inc.octane.iam.domain.model.commands.SignInCommand;
import nodrify.inc.octane.iam.domain.model.commands.SignUpCommand;
import nodrify.inc.octane.iam.domain.model.commands.UpdateUserCommand;
import nodrify.inc.octane.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {

    void handle(DeleteUserCommand deleteUserCommand);

    Optional<User> handle(UpdateUserCommand updateUserCommand , Long userId);

    Optional<ImmutablePair<User,String>> handle(SignInCommand signInCommand);

    Optional<User> handle(SignUpCommand signUpCommand);

}
