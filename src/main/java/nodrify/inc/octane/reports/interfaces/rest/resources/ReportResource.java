package nodrify.inc.octane.reports.interfaces.rest.resources;

import nodrify.inc.octane.maintenance.interfaces.rest.resources.MaintenanceResource;
import nodrify.inc.octane.assignments.interfaces.rest.resources.AssignmentResource;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.VehicleResource;

import java.util.List;

public record ReportResource(
        VehicleResource vehicle,
        List<MaintenanceResource> maintenances,
        AssignmentResource assignment
) {
}
