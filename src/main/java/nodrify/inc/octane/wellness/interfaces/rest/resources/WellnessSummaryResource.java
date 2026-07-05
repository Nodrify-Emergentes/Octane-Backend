package nodrify.inc.octane.wellness.interfaces.rest.resources;

import java.util.Date;

public record WellnessSummaryResource(
        Long id,
        Long vehicleId,
        String status,
        String summary,
        Date updatedAt
) {
}
