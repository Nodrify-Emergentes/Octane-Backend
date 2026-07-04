package nodrify.inc.octane.maintenance.application.internal.queryservices;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllMaintenancesByMechanicIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetMaintenanceByIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetMaintenancesByOwnerIdQuery;
import nodrify.inc.octane.maintenance.domain.services.MaintenanceQueryService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceRepository;
import nodrify.inc.octane.shared.domain.model.entity.AuditableModel;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.VehicleReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceQueryServiceImpl implements MaintenanceQueryService {

    private final MaintenanceRepository maintenanceRepository;
    private final OwnerRepository ownerRepository;
    private final VehicleReadRepository vehicleReadRepository;

    public MaintenanceQueryServiceImpl(MaintenanceRepository maintenanceRepository, OwnerRepository ownerRepository, VehicleReadRepository vehicleReadRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.ownerRepository = ownerRepository;
        this.vehicleReadRepository = vehicleReadRepository;
    }

    @Override
    public List<Maintenance> handle(GetAllMaintenancesByVehicleIdQuery query) {
        return maintenanceRepository.findByVehicleId(query.vehicleId());
    }

    @Override
    public Optional<Maintenance> handle(GetMaintenanceByIdQuery query) {
        return maintenanceRepository.findById(query.maintenanceId());
    }

    @Override
    public List<Maintenance> handle(GetAllMaintenancesByMechanicIdQuery query) {
        return maintenanceRepository.findByMechanicId(query.mechanicId());
    }

    @Override
    public List<Maintenance> handle(GetMaintenancesByOwnerIdQuery query) {

        var owner = ownerRepository.findById(query.ownerId());

        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner with ID " + query.ownerId() + " not found.");
        }

        var ownerVehicles = vehicleReadRepository.getVehiclesByOwner_Id(owner.get().getId());
        if (ownerVehicles == null ) {
            throw new IllegalArgumentException("Owner with ID " + query.ownerId() + " has no vehicles.");
        }

        var vehicleIds = ownerVehicles.stream()
                .map(AuditableModel::getId)
                .toList();

        return maintenanceRepository.findByVehicleIdIn(vehicleIds);
    }
}
