package nodrify.inc.octane.wellness.application.internal.queryservices;

import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllWellnessSummariesByOwnerIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessSummaryByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryQueryService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.WellnessSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WellnessSummaryQueryServiceImpl implements WellnessSummaryQueryService {
    private final WellnessSummaryRepository wellnessSummaryRepository;
    private final ExternalVehiclesService externalVehiclesService;

    public WellnessSummaryQueryServiceImpl(WellnessSummaryRepository wellnessSummaryRepository, ExternalVehiclesService externalVehiclesService) {
        this.wellnessSummaryRepository = wellnessSummaryRepository;
        this.externalVehiclesService = externalVehiclesService;
    }

    @Override
    public Optional<WellnessSummary> handle(GetWellnessSummaryByVehicleIdQuery getWellnessSummaryByVehicleIdQuery) {
        return wellnessSummaryRepository.findByVehicleId(getWellnessSummaryByVehicleIdQuery.vehicleId());
    }

    @Override
    public List<WellnessSummary> handle(GetAllWellnessSummariesByOwnerIdQuery getAllWellnessSummariesByOwnerIdQuery) {
        var id = getAllWellnessSummariesByOwnerIdQuery.ownerId().ownerId();
        var ownerOpt = externalVehiclesService.getOwnerById(id);

        if (ownerOpt.isEmpty()) {
            return List.of();
        }

        var vehicles = ownerOpt.get().GetVehicles();

        return vehicles.stream()
                .map(vehicle -> wellnessSummaryRepository.findByVehicleId(new VehicleId(vehicle.getId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
