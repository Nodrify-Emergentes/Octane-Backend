package nodrify.inc.octane.vehicles.interfaces.rest.resources;

public record VehicleResource(
        Long id,
        OwnerResource owner,
        ModelResource model,
        String plate,
        String year
) {

}
