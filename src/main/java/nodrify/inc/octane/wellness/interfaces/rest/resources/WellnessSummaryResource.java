package nodrify.inc.octane.wellness.interfaces.rest.resources;

public record WellnessSummaryResource(
        Long id,
        Long vehicleId,
        String status,
        String summary
) {
}
