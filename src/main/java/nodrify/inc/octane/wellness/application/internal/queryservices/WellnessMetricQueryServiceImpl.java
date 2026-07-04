package nodrify.inc.octane.wellness.application.internal.queryservices;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllWellnessMetricsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricQueryService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.WellnessMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WellnessMetricQueryServiceImpl implements WellnessMetricQueryService
{
    private final WellnessMetricRepository wellnessMetricRepository;

    public WellnessMetricQueryServiceImpl(WellnessMetricRepository wellnessMetricRepository) {
        this.wellnessMetricRepository = wellnessMetricRepository;
    }

    @Override
    public Optional<WellnessMetric> handle(GetWellnessMetricByIdQuery getWellnessMetricByIdQuery) {
        return wellnessMetricRepository.findById(getWellnessMetricByIdQuery.wellnessMetricId());
    }

    @Override
    public List<WellnessMetric> handle(GetAllWellnessMetricsQuery getAllWellnessMetricsQuery) {
        return wellnessMetricRepository.findAll();
    }

    @Override
    public List<WellnessMetric> handle(GetWellnessMetricsByVehicleIdQuery getWellnessMetricsByVehicleIdQuery) {
        return wellnessMetricRepository.findByVehicleId(getWellnessMetricsByVehicleIdQuery.vehicleId());
    }
}
