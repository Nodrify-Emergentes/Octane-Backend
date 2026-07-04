package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllMaintenancesByMechanicIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetMaintenanceByIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetMaintenancesByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface MaintenanceQueryService {
    List<Maintenance> handle(GetAllMaintenancesByVehicleIdQuery query);
    Optional<Maintenance> handle(GetMaintenanceByIdQuery query);
    List<Maintenance> handle(GetAllMaintenancesByMechanicIdQuery query);
    List<Maintenance> handle(GetMaintenancesByOwnerIdQuery query);
}
