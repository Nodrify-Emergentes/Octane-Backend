package nodrify.inc.octane.maintenance.interfaces.rest.resources;

public record MaintenanceResource(
        Long id,
        String details,
        Long vehicleId,
        String dateOfService,
        String location,
        String description,
        String state,
        ExpenseResource expense,
        Long mechanicId
) {
}
