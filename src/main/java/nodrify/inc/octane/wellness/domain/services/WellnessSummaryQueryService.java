package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessSummaryByVehicleIdQuery;

import java.util.Optional;

public interface WellnessSummaryQueryService {
    Optional<WellnessSummary> handle(GetWellnessSummaryByVehicleIdQuery getWellnessSummaryByVehicleIdQuery);
}
