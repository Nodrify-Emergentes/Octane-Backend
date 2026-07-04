package nodrify.inc.octane.vehicles.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nodrify.inc.octane.vehicles.domain.model.commands.DeleteVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.queries.GetOwnerByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByIdQuery;
import nodrify.inc.octane.vehicles.domain.services.OwnerCommandService;
import nodrify.inc.octane.vehicles.domain.services.OwnerQueryService;
import nodrify.inc.octane.vehicles.domain.services.VehiclesQueryService;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.AddVehicleResource;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.VehicleResource;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.AddVehicleCommandFromResourceAssembler;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicles", description = "Operations related to vehicles")
public class VehiclesController {

    private final OwnerQueryService ownerQueryService;
    private final OwnerCommandService ownerCommandService;
    private final VehiclesQueryService vehiclesQueryService;

    public VehiclesController(
            OwnerQueryService ownerQueryService,
            VehiclesQueryService vehiclesQueryService,
            OwnerCommandService ownerCommandService
    ) {
        this.ownerQueryService = ownerQueryService;
        this.vehiclesQueryService = vehiclesQueryService;
        this.ownerCommandService = ownerCommandService;
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get vehicles by owner ID", description = "Retrieve all vehicles associated with a specific owner ID")
    public ResponseEntity<List<VehicleResource>> getVehiclesByOwnerId(@PathVariable Long ownerId) {
        var ownerOpt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var owner = ownerOpt.get();
        var vehicles = owner.GetVehicles();
        var vehicleResources = vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(vehicleResources);
    }

    @GetMapping("/{vehicleId}")
    @Operation(summary = "Get vehicle by ID", description = "Retrieve a vehicle using its unique ID")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long vehicleId) {
        var vehicleOpt = vehiclesQueryService.handle(new GetVehicleByIdQuery(vehicleId));
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicleOpt.get());
        return ResponseEntity.ok(vehicleResource);
    }


    @PostMapping("/{ownerId}")
    @Operation(summary = "Add a new vehicle to an owner", description = "Create and associate a new vehicle with a specific owner ID")
    public ResponseEntity<VehicleResource> addVehicleToOwner(
            @PathVariable Long ownerId,
            @RequestBody AddVehicleResource resource
    ) {
        var ownerOpt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var command = AddVehicleCommandFromResourceAssembler.toCommandFromResource(ownerId, resource);
        var vehicleOpt = ownerCommandService.handle(command);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicleOpt.get());
        return ResponseEntity.ok(vehicleResource);
    }


    @DeleteMapping("/{vehicleId}")
    @Operation(summary = "Delete a vehicle by ID", description = "Remove a vehicle from the system using its unique ID")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long vehicleId) {
        var vehicleOpt = vehiclesQueryService.handle(new GetVehicleByIdQuery(vehicleId));
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var ownerOpt = ownerQueryService.handle(new GetOwnerByIdQuery(vehicleOpt.get().getOwner().getId()));
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var command = new DeleteVehicleFromOwnerCommand(ownerOpt.get().getId(), vehicleId);
        this.ownerCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

}
