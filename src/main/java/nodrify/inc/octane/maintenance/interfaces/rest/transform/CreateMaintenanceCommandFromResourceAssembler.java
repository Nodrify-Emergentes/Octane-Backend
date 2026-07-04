package nodrify.inc.octane.maintenance.interfaces.rest.transform;

import nodrify.inc.octane.maintenance.domain.model.commands.CreateMaintenanceCommand;
import nodrify.inc.octane.maintenance.interfaces.rest.resources.CreateMaintenanceResource;

public class CreateMaintenanceCommandFromResourceAssembler {
    public static CreateMaintenanceCommand toCommandFromResource(CreateMaintenanceResource resource) {
        return new CreateMaintenanceCommand(
                resource.details(),
                resource.vehicleId(),
                resource.dateOfService(),
                resource.location(),
                resource.description(),
                resource.mechanicId()
        );
    }
}
