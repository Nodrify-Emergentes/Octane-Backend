package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.commands.UpdateUserCommand;
import nodrify.inc.octane.iam.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(UpdateUserResource updateUserResource){
        return new UpdateUserCommand(
                updateUserResource.username(),
                updateUserResource.password());
    }
}
