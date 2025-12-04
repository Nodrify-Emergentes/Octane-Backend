package nrg.inc.bykerz.maintenance.domain.services;

import nrg.inc.bykerz.maintenance.domain.model.agreggates.Maintenance;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByMechanicIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenanceByIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenancesByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface MaintenanceQueryService {
    List<Maintenance> handle(GetAllMaintenancesByVehicleIdQuery query);
    Optional<Maintenance> handle(GetMaintenanceByIdQuery query);
    List<Maintenance> handle(GetAllMaintenancesByMechanicIdQuery query);
    List<Maintenance> handle(GetMaintenancesByOwnerIdQuery query);
}
