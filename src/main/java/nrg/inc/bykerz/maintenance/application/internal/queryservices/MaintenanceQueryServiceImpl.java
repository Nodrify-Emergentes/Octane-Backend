package nrg.inc.bykerz.maintenance.application.internal.queryservices;

import nrg.inc.bykerz.maintenance.domain.model.agreggates.Maintenance;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByMechanicIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenanceByIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenancesByOwnerIdQuery;
import nrg.inc.bykerz.maintenance.domain.services.MaintenanceQueryService;
import nrg.inc.bykerz.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceRepository;
import nrg.inc.bykerz.shared.domain.model.entity.AuditableModel;
import nrg.inc.bykerz.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import nrg.inc.bykerz.vehicles.infrastructure.persistence.jpa.repositories.VehicleReadRepository;
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
