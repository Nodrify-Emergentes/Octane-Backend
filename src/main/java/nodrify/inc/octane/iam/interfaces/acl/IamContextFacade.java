package nodrify.inc.octane.iam.interfaces.acl;

import nodrify.inc.octane.iam.domain.model.aggregates.User;

import java.util.Optional;

public interface IamContextFacade {
    Optional<User> fetchUserById(Long userId);

    Optional<User> fetchUserByUsername(String username);
}
