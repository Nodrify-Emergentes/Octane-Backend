package nodrify.inc.octane.wellness.application.internal.queryservices;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessSummaryByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryQueryService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.WellnessSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WellnessSummaryQueryServiceImpl implements WellnessSummaryQueryService {
    private final WellnessSummaryRepository wellnessSummaryRepository;

    public WellnessSummaryQueryServiceImpl(WellnessSummaryRepository wellnessSummaryRepository) {
        this.wellnessSummaryRepository = wellnessSummaryRepository;
    }

    @Override
    public Optional<WellnessSummary> handle(GetWellnessSummaryByVehicleIdQuery getWellnessSummaryByVehicleIdQuery) {
        return wellnessSummaryRepository.findByVehicleId(getWellnessSummaryByVehicleIdQuery.vehicleId());
    }
}
