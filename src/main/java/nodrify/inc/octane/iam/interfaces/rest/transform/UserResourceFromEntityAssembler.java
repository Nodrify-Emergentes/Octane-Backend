package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.domain.model.entities.Role;
import nodrify.inc.octane.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getUserRoles().stream().map(Role::getName).toList()
        );
    }

}
