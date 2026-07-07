package nodrify.inc.octane.assignments.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nodrify.inc.octane.assignments.domain.model.queries.GetAssignmentByOwnerIdQuery;
import nodrify.inc.octane.assignments.domain.services.AssignmentCommandService;
import nodrify.inc.octane.assignments.domain.services.AssignmentQueryService;
import nodrify.inc.octane.assignments.interfaces.rest.resources.AssignmentResource;
import nodrify.inc.octane.assignments.interfaces.rest.transform.AssignmentResourceFromEntityAssembler;
import nodrify.inc.octane.assignments.domain.model.queries.GetMechanicByIdQuery;
import nodrify.inc.octane.assignments.domain.services.MechanicQueryService;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/owner/{ownerId}/assignment")
@Tag(name= "Assignments", description = "Assignment Management API")
public class OwnerAssigmentController {
    private final AssignmentQueryService assignmentQueryService;
    private final AssignmentCommandService assignmentCommandService;
    private final MechanicQueryService mechanicQueryService;
    private final ExternalVehiclesService externalVehiclesService;

    public OwnerAssigmentController(AssignmentQueryService assignmentQueryService, AssignmentCommandService assignmentCommandService, MechanicQueryService mechanicQueryService, ExternalVehiclesService externalVehiclesService) {
        this.assignmentQueryService = assignmentQueryService;
        this.assignmentCommandService = assignmentCommandService;
        this.mechanicQueryService = mechanicQueryService;
        this.externalVehiclesService = externalVehiclesService;
    }

    @GetMapping()
    @Operation(summary = "Get Assignment for an owner", description = "Get the assignment associated to the specified owner.")
    public ResponseEntity<AssignmentResource> getAssignment(@PathVariable Long ownerId) {
        var ownerOpt = this.externalVehiclesService.getOwnerById(ownerId);
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var owner = ownerOpt.get();
        var assignmentOpt = this.assignmentQueryService.handle(new GetAssignmentByOwnerIdQuery(ownerId));
        if (assignmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var assignment = assignmentOpt.get();
        var mechanicOpt = this.mechanicQueryService.handle(new GetMechanicByIdQuery(assignment.getMechanic().getId()));
        if (mechanicOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var mechanic = mechanicOpt.get();
        var assignmentResource = AssignmentResourceFromEntityAssembler.toResourceFromEntity(assignment, mechanic, owner);
        return ResponseEntity.ok(assignmentResource);
    }
}
