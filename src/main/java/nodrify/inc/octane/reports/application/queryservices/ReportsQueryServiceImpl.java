package nodrify.inc.octane.reports.application.queryservices;

import nodrify.inc.octane.assignments.domain.model.queries.GetAssignmentByOwnerIdQuery;
import nodrify.inc.octane.assignments.domain.model.queries.GetMechanicByIdQuery;
import nodrify.inc.octane.assignments.domain.services.AssignmentQueryService;
import nodrify.inc.octane.assignments.domain.services.MechanicQueryService;
import nodrify.inc.octane.assignments.interfaces.rest.resources.AssignmentResource;
import nodrify.inc.octane.assignments.interfaces.rest.transform.AssignmentResourceFromEntityAssembler;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nodrify.inc.octane.maintenance.domain.services.MaintenanceQueryService;
import nodrify.inc.octane.maintenance.interfaces.rest.resources.MaintenanceResource;
import nodrify.inc.octane.maintenance.interfaces.rest.transform.MaintenanceResourceFromEntityAssembler;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByIdQuery;
import nodrify.inc.octane.vehicles.domain.services.VehiclesQueryService;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.VehicleResource;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import nodrify.inc.octane.reports.domain.model.queries.GetReportByVehicleIdQuery;
import nodrify.inc.octane.reports.domain.services.ReportsQueryService;
import nodrify.inc.octane.reports.interfaces.rest.resources.ReportResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportsQueryServiceImpl implements ReportsQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsQueryServiceImpl.class);

    private final VehiclesQueryService vehiclesQueryService;
    private final MaintenanceQueryService maintenanceQueryService;
    private final AssignmentQueryService assignmentQueryService;
    private final MechanicQueryService mechanicQueryService;
    private final ExternalVehiclesService externalVehiclesService;

    public ReportsQueryServiceImpl(
            VehiclesQueryService vehiclesQueryService,
            MaintenanceQueryService maintenanceQueryService,
            AssignmentQueryService assignmentQueryService,
            MechanicQueryService mechanicQueryService,
            ExternalVehiclesService externalVehiclesService
    ) {
        this.vehiclesQueryService = vehiclesQueryService;
        this.maintenanceQueryService = maintenanceQueryService;
        this.assignmentQueryService = assignmentQueryService;
        this.mechanicQueryService = mechanicQueryService;
        this.externalVehiclesService = externalVehiclesService;
    }

    @Override
    public Optional<ReportResource> handle(GetReportByVehicleIdQuery query) {
        Long vehicleId = query.vehicleId();

        // 1. Obtener el vehículo
        var vehicleOpt = vehiclesQueryService.handle(new GetVehicleByIdQuery(vehicleId));
        if (vehicleOpt.isEmpty()) {
            return Optional.empty();
        }
        var vehicle = vehicleOpt.get();
        VehicleResource vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);

        // 2. Obtener el ownerId del vehículo
        Long ownerId = vehicle.getOwner().getId();

        // 3. Obtener los mantenimientos del vehículo
        List<MaintenanceResource> maintenancesResources = maintenanceQueryService
                .handle(new GetAllMaintenancesByVehicleIdQuery(vehicleId))
                .stream()
                .map(MaintenanceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        // 4. Obtener la asignación por ownerId (refactorización)
        AssignmentResource assignmentResource = null;
        var assignmentOpt = assignmentQueryService.handle(new GetAssignmentByOwnerIdQuery(ownerId));

        if (assignmentOpt.isPresent()) {
            var assignment = assignmentOpt.get();
            var mechanicOpt = mechanicQueryService.handle(new GetMechanicByIdQuery(assignment.getMechanic().getId()));

            if (mechanicOpt.isPresent()) {
                var mechanic = mechanicOpt.get();
                var owner = externalVehiclesService.getOwnerById(ownerId).orElse(null);

                assignmentResource = AssignmentResourceFromEntityAssembler.toResourceFromEntity(
                        assignment,
                        mechanic,
                        owner
                );
            }
        }

        var report = new ReportResource(vehicleResource, maintenancesResources, assignmentResource);
        return Optional.of(report);
    }
}
