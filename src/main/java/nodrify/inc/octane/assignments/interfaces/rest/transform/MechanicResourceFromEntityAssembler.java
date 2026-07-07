package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.assignments.interfaces.rest.resources.MechanicResource;

public class MechanicResourceFromEntityAssembler {
    public static MechanicResource toResourceFromEntity(Mechanic entity) {
        return new MechanicResource(
                entity.getId(),
                entity.getName(),
                entity.getMembershipType()
        );
    }
}
