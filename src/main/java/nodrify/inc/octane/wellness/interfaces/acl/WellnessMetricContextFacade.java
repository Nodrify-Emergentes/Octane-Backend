package nodrify.inc.octane.wellness.interfaces.acl;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;

import java.util.Optional;

public interface WellnessMetricContextFacade {
    Optional<WellnessMetric> fetchWellnessMetricById(Long wellnessMetricId);
}
