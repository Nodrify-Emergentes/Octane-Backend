package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.aggregates.Assignment;
import nodrify.inc.octane.assignments.interfaces.rest.resources.AssignmentResource;
import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.OwnerResourceFromEntityAssembler;

public class AssignmentResourceFromEntityAssembler {
    public static AssignmentResource toResourceFromEntity(Assignment entity, Mechanic mechanic, Owner owner) {
        return new AssignmentResource(
                entity.getId(),
                owner == null ? null : OwnerResourceFromEntityAssembler.toResourceFromEntity(owner),
                MechanicResourceFromEntityAssembler.toResourceFromEntity(mechanic),
                entity.getType().toString(),
                entity.getStatus().toString(),
                entity.getAssignmentCode().code().toString(),
                entity.getCreatedAt()
        );
    }
}
