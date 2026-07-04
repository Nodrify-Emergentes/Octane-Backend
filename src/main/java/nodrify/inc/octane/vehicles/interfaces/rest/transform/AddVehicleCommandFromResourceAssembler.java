package nodrify.inc.octane.vehicles.interfaces.rest.transform;

import nodrify.inc.octane.vehicles.domain.model.commands.AddVehicleToOwnerCommand;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.AddVehicleResource;

public class AddVehicleCommandFromResourceAssembler {
    public static AddVehicleToOwnerCommand toCommandFromResource(Long ownerId, AddVehicleResource resource) {
        return new AddVehicleToOwnerCommand(
                ownerId,
                resource.modelId(),
                resource.year(),
                resource.plate()
        );
    }
}
