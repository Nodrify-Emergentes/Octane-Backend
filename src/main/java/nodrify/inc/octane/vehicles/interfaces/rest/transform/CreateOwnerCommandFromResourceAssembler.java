package nodrify.inc.octane.vehicles.interfaces.rest.transform;

import nodrify.inc.octane.vehicles.domain.model.commands.CreateOwnerCommand;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.CreateOwnerResource;

public class CreateOwnerCommandFromResourceAssembler {
    public static CreateOwnerCommand toCommandFromResource(CreateOwnerResource resource) {
        return new CreateOwnerCommand(
                resource.profileId()
        );
    }
}
