package nodrify.inc.octane.maintenance.interfaces.rest.resources;

import java.util.Date;

public record CreateMaintenanceResource(
        String details,
        Long vehicleId,
        Date dateOfService,
        String location,
        String description,
        Long mechanicId
) {
}
