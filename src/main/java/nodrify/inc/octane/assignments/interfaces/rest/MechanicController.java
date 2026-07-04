package nodrify.inc.octane.assignments.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nodrify.inc.octane.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;
import nodrify.inc.octane.assignments.domain.model.queries.GetAssignmentsByMechanicIdAndStatusQuery;
import nodrify.inc.octane.assignments.domain.model.queries.GetMechanicByIdQuery;
import nodrify.inc.octane.assignments.domain.services.AssignmentQueryService;
import nodrify.inc.octane.assignments.domain.services.MechanicCommandService;
import nodrify.inc.octane.assignments.domain.services.MechanicQueryService;
import nodrify.inc.octane.assignments.interfaces.rest.resources.MechanicResource;
import nodrify.inc.octane.assignments.interfaces.rest.resources.UpdateMechanicMembershipTypeResource;
import nodrify.inc.octane.assignments.interfaces.rest.transform.MechanicResourceFromEntityAssembler;
import nodrify.inc.octane.assignments.interfaces.rest.transform.UpdateMechanicMembershipTypeCommandFromResourceAssembler;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.OwnerResource;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.OwnerResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/mechanic")
@Tag(name= "Mechanic", description = "Mechanic Management API")
public class MechanicController {
    private final AssignmentQueryService assignmentQueryService;
    private final MechanicQueryService mechanicQueryService;
    private final MechanicCommandService mechanicCommandService;
    private final ExternalVehiclesService externalVehiclesService;
    public MechanicController(AssignmentQueryService assignmentQueryService, MechanicQueryService mechanicQueryService,MechanicCommandService mechanicCommandService, ExternalVehiclesService externalVehiclesService) {
        this.assignmentQueryService = assignmentQueryService;
        this.mechanicQueryService = mechanicQueryService;
        this.externalVehiclesService = externalVehiclesService;
        this.mechanicCommandService = mechanicCommandService;
    }

    @GetMapping("{mechanicId}/owners")
    @Operation(summary = "Get Owners for Mechanic", description = "Get all vehicle owners associated to the specified mechanic's assignments.")
    public ResponseEntity<List<OwnerResource>> getOwnersForMechanic(@PathVariable Long mechanicId) {
        var mechanicOpt = this.mechanicQueryService.handle(new GetMechanicByIdQuery(mechanicId));
        if (mechanicOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var assignments = this.assignmentQueryService.handle(
                new GetAssignmentsByMechanicIdAndStatusQuery(mechanicId, "ACTIVE")
        );
        if (assignments.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        var ownerResources = assignments.stream()
                .map(a -> {
                    var ownerId = a.getOwnerId();
                    if (ownerId == null) return null;
                    return this.externalVehiclesService.getOwnerById(ownerId) // Optional<OwnerEntity> or Optional<Owner>
                            .map(owner -> OwnerResourceFromEntityAssembler.toResourceFromEntity(owner)) // convert entity -> OwnerResource
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        return ResponseEntity.ok(ownerResources);
    }

    @PutMapping("{mechanicId}/membership")
    @Operation(summary = "Update Mechanic Membership Type", description = "Update the membership type of the specified mechanic.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Membership type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Mechanic not found")
    }
    )
    public ResponseEntity<MechanicResource> updateMechanicMembershipType(
            @PathVariable Long mechanicId,
            @RequestBody UpdateMechanicMembershipTypeResource updateMechanicMembershipTypeResource) {

        //Create the command from resource
        UpdateMechanicMembershipTypeCommand command = UpdateMechanicMembershipTypeCommandFromResourceAssembler.toCommandFromResource(mechanicId,updateMechanicMembershipTypeResource);
        // Handle the command by the service
        var updatedMechanicOpt = mechanicCommandService.handle(command);
        // Check if the mechanic was found and updated
        if (updatedMechanicOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        } else {
            var updatedMechanic = updatedMechanicOpt.get();
            var updatedMechanicResource = MechanicResourceFromEntityAssembler.toResourceFromEntity(updatedMechanic);
            return ResponseEntity.status(200).body(updatedMechanicResource);
        }



    }
}
