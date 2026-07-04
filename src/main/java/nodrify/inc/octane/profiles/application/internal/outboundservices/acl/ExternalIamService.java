package nodrify.inc.octane.profiles.application.internal.outboundservices.acl;

import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    public Optional<User> getUserById(Long id) {
        var userOpt = iamContextFacade.fetchUserById(id);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        return  Optional.of(userOpt.get());
    }

    public Optional<User> getUserByUsername(String username) {
        return iamContextFacade.fetchUserByUsername(username);
    }
}
