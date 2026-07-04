package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.commands.CreateUserCommand;
import nodrify.inc.octane.iam.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource createUserResource) {
        return new CreateUserCommand(
                createUserResource.username(),
                createUserResource.password(),
                createUserResource.roles().stream().toList());
    }
}
