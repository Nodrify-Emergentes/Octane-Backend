package nodrify.inc.octane.assignments.interfaces.rest.resources;

import nodrify.inc.octane.vehicles.interfaces.rest.resources.OwnerResource;

import java.util.Date;

public record AssignmentResource(
        Long id,
        OwnerResource owner,
        MechanicResource mechanic,
        String type,
        String status,
        String assignmentCode,
        Date createdAt
) {
}
