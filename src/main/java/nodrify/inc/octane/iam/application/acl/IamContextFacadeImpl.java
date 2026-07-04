package nodrify.inc.octane.iam.application.acl;

import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.domain.model.queries.GetUserByIdQuery;
import nodrify.inc.octane.iam.domain.model.queries.GetUserByUsernameQuery;
import nodrify.inc.octane.iam.domain.services.UserQueryService;
import nodrify.inc.octane.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<User> fetchUserById(Long userId) {
        if( userId == null || userId <= 0) {
            return Optional.empty();
        }
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get());
    }

    @Override
    public Optional<User> fetchUserByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var user = userQueryService.handle(getUserByUsernameQuery);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(user.get());
    }
}
