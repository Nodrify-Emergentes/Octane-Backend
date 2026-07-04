package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllWellnessMetricsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricsByVehicleIdQuery;

import java.util.List;
import java.util.Optional;

public interface WellnessMetricQueryService {

    Optional<WellnessMetric> handle(GetWellnessMetricByIdQuery getWellnessMetricByIdQuery);

    List<WellnessMetric> handle (GetAllWellnessMetricsQuery getAllWellnessMetricsQuery);

    List<WellnessMetric> handle(GetWellnessMetricsByVehicleIdQuery getWellnessMetricsByVehicleIdQuery);


}
