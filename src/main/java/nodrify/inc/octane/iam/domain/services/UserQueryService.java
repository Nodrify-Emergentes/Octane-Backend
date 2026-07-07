package nodrify.inc.octane.iam.domain.services;

import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.domain.model.queries.GetAllUsersQuery;
import nodrify.inc.octane.iam.domain.model.queries.GetUserByIdQuery;
import nodrify.inc.octane.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery getAllUsersQuery);

    Optional<User> handle(GetUserByIdQuery getUserByIdQuery);

    Optional<User> handle(GetUserByUsernameQuery getUserByUserNameQuery);

}
