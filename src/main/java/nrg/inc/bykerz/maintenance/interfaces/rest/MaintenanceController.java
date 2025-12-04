package nrg.inc.bykerz.maintenance.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.bykerz.iam.domain.services.UserQueryService;
import nrg.inc.bykerz.maintenance.domain.model.commands.AssignExpenseToMaintenanceCommand;
import nrg.inc.bykerz.maintenance.domain.model.commands.DeleteMaintenanceCommand;
import nrg.inc.bykerz.maintenance.domain.model.commands.UpdateStateOfMaintenanceByIdCommand;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByMechanicIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllMaintenancesByVehicleIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenanceByIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetMaintenancesByOwnerIdQuery;
import nrg.inc.bykerz.maintenance.domain.services.MaintenanceCommandService;
import nrg.inc.bykerz.maintenance.domain.services.MaintenanceQueryService;
import nrg.inc.bykerz.maintenance.interfaces.rest.resources.CreateMaintenanceResource;
import nrg.inc.bykerz.maintenance.interfaces.rest.resources.MaintenanceResource;
import nrg.inc.bykerz.maintenance.interfaces.rest.resources.UpdateStatusOfMaintenanceResource;
import nrg.inc.bykerz.maintenance.interfaces.rest.transform.CreateMaintenanceCommandFromResourceAssembler;
import nrg.inc.bykerz.maintenance.interfaces.rest.transform.MaintenanceResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/maintenance")
@Tag(name = "Maintenance", description = "Maintenance management API")
public class MaintenanceController {

    private final MaintenanceQueryService maintenanceQueryService;
    private final MaintenanceCommandService maintenanceCommandService;
    private final UserQueryService userQueryService;

    public MaintenanceController(MaintenanceQueryService maintenanceQueryService, MaintenanceCommandService maintenanceCommandService, UserQueryService userQueryService) {
        this.maintenanceQueryService = maintenanceQueryService;
        this.maintenanceCommandService = maintenanceCommandService;
        this.userQueryService = userQueryService;
    }

    @GetMapping("/{maintenanceId}")
    @Operation(summary = "Get Maintenance by ID", description = "Retrieve maintenance details by its ID")
    public ResponseEntity<MaintenanceResource> getMaintenanceById(@PathVariable Long maintenanceId){
        var getMaintenanceByIdQuery = new GetMaintenanceByIdQuery(maintenanceId);
        var maintenance = maintenanceQueryService.handle(getMaintenanceByIdQuery);

        if (maintenance.isEmpty()) {return ResponseEntity.notFound().build();}

        var maintenanceResource = MaintenanceResourceFromEntityAssembler.toResourceFromEntity(maintenance.get());

        return ResponseEntity.ok(maintenanceResource);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get Maintenances By Vehicle Id", description = "Get all the maintenances of a vehicle by its id" )
    public ResponseEntity<List<MaintenanceResource>> getMaintenancesByUserId(@PathVariable Long vehicleId){

        var maintenancesList = maintenanceQueryService.handle(new GetAllMaintenancesByVehicleIdQuery(vehicleId));

        var maintenancesResources = maintenancesList.stream()
                .map(MaintenanceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(maintenancesResources);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get Maintenances By Owner Id", description = "Get all the maintenances of a vehicle by an owner id" )
    public ResponseEntity<List<MaintenanceResource>> getMaintenancesByOwnerId(@PathVariable Long ownerId){

        var maintenancesList = maintenanceQueryService.handle(new GetMaintenancesByOwnerIdQuery(ownerId));

        var maintenancesResources = maintenancesList.stream()
                .map(MaintenanceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(maintenancesResources);

    }


    @DeleteMapping("/{maintenanceId}")
    @Operation(summary = "Delete Maintenance By Id", description = "delete a maintenance by its id")
    public ResponseEntity<Void> deleteMaintenanceById(@PathVariable Long maintenanceId){

        var deleteMaintenanceCommand = new DeleteMaintenanceCommand(maintenanceId);

        maintenanceCommandService.handle(deleteMaintenanceCommand);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create a new Maintenance", description = "Create a new maintenance ")
    public ResponseEntity<MaintenanceResource> createMaintenance(@RequestBody CreateMaintenanceResource resource) {
        var createMaintenanceCommand = CreateMaintenanceCommandFromResourceAssembler.toCommandFromResource(resource);

        var maintenance = maintenanceCommandService.handle(createMaintenanceCommand);

        if (maintenance.isEmpty()) {return ResponseEntity.badRequest().build();}

        var maintenanceResource = MaintenanceResourceFromEntityAssembler.toResourceFromEntity(maintenance.get());

        return ResponseEntity.ok(maintenanceResource);
    }

    @PutMapping("/{maintenanceId}")
    @Operation(summary = "Update Maintenance status By Id", description = "Update a status of a maintenance by its id")
    public ResponseEntity<MaintenanceResource> updateMaintenanceStatusById(
            @PathVariable Long maintenanceId,
            @RequestBody UpdateStatusOfMaintenanceResource resource){

        var updateStateOfMaintenanceByIdCommand = new UpdateStateOfMaintenanceByIdCommand(
                resource.newStatus(),
                maintenanceId
        );

        var maintenance = maintenanceCommandService.handle(updateStateOfMaintenanceByIdCommand);

        if (maintenance.isEmpty()) {return ResponseEntity.badRequest().build();}

        var maintenanceResource = MaintenanceResourceFromEntityAssembler.toResourceFromEntity(maintenance.get());

        return ResponseEntity.ok(maintenanceResource);
    }

    @PutMapping("/{maintenanceId}/expense/assign/{expenseId}")
    @Operation(summary = "Assign Expense to Maintenance" , description = "Assign an expense to a maintenance by their ids")
    public ResponseEntity<MaintenanceResource> assignExpenseToMaintenance(
            @PathVariable Long maintenanceId, @PathVariable Long expenseId){
        var assignExpenseToMaintenanceCommand = new AssignExpenseToMaintenanceCommand(
                maintenanceId, expenseId);

        var maintenance = maintenanceCommandService.handle(assignExpenseToMaintenanceCommand);

        if (maintenance.isEmpty()) {return ResponseEntity.badRequest().build();}

        var maintenanceResource = MaintenanceResourceFromEntityAssembler.toResourceFromEntity(maintenance.get());

        return ResponseEntity.ok(maintenanceResource);
    }

    @GetMapping("/mechanic/{mechanicId}")
    @Operation(summary = "Get Maintenances By Mechanic Id", description = "Get all the maintenances assigned to a mechanic by his id" )
    public ResponseEntity<List<MaintenanceResource>> getMaintenancesByMechanicId(@PathVariable Long mechanicId){
        var maintenancesList = maintenanceQueryService.handle(new GetAllMaintenancesByMechanicIdQuery(mechanicId));

        var maintenancesResources = maintenancesList.stream()
                .map(MaintenanceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(maintenancesResources);
    }

}
