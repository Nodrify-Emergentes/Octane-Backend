package nodrify.inc.octane.wellness.interfaces.rest.transform;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.interfaces.rest.resources.WellnessSummaryResource;

public class WellnessSummaryResourceFromEntityAssembler {
    public static WellnessSummaryResource toResourceFromEntity(WellnessSummary entity) {
        return new WellnessSummaryResource(
                entity.getId(),
                entity.getVehicleId(),
                entity.getSummaryStatus(),
                entity.getSummary()
        );
    }
}
