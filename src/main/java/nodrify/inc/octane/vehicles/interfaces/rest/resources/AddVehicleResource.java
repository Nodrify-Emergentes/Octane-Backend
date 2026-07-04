package nodrify.inc.octane.vehicles.interfaces.rest.resources;

public record AddVehicleResource(
        String plate,
        String year,
        Long modelId
) {
}
