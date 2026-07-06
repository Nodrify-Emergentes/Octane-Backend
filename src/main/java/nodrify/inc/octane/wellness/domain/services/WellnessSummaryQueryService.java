package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllWellnessSummariesByOwnerIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessSummaryByVehicleIdQuery;

import java.util.List;
import java.util.Optional;

public interface WellnessSummaryQueryService {
    Optional<WellnessSummary> handle(GetWellnessSummaryByVehicleIdQuery getWellnessSummaryByVehicleIdQuery);
    List<WellnessSummary> handle(GetAllWellnessSummariesByOwnerIdQuery getAllWellnessSummariesByOwnerIdQuery);
}
