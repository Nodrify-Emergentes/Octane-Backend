package nodrify.inc.octane.wellness.application.acl;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricByIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricQueryService;
import nodrify.inc.octane.wellness.interfaces.acl.WellnessMetricContextFacade;

import java.util.Optional;

public class WellnessMetricContextFacadeImpl implements WellnessMetricContextFacade {

    private final WellnessMetricQueryService wellnessMetricQueryService;

    public WellnessMetricContextFacadeImpl(WellnessMetricQueryService wellnessMetricQueryService) {
        this.wellnessMetricQueryService = wellnessMetricQueryService;
    }


    @Override
    public Optional<WellnessMetric> fetchWellnessMetricById(Long wellnessMetricId) {
        return wellnessMetricQueryService.handle(new GetWellnessMetricByIdQuery(wellnessMetricId));
    }
}
