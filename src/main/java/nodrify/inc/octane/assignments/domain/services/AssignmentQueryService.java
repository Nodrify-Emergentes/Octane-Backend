package nodrify.inc.octane.assignments.domain.services;

import nodrify.inc.octane.assignments.domain.model.aggregates.Assignment;
import nodrify.inc.octane.assignments.domain.model.queries.*;
import nodrify.inc.octane.assignments.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface AssignmentQueryService {
    Optional<Assignment> handle(GetAssignmentByOwnerIdQuery query);
    List<Assignment> handle(GetAssignmentsByMechanicIdAndStatusQuery query);
    Optional<Assignment> handle(GetAssignmentByIdQuery query);
    Optional<Assignment> handle(GetAssigmentByCodeQuery query);
    Optional<Assignment> handle(GetAssignmentByVehicleIdQuery getAssignmentByVehicleIdQuery);
}
