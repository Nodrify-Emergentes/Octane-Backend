package nrg.inc.bykerz.assignments.interfaces.rest.transform;

import nrg.inc.bykerz.assignments.domain.model.aggregates.Mechanic;
import nrg.inc.bykerz.assignments.interfaces.rest.resources.MechanicResource;

public class MechanicResourceFromEntityAssembler {
    public static MechanicResource toResourceFromEntity(Mechanic entity) {
        return new MechanicResource(
                entity.getId(),
                entity.getName(),
                entity.getMembershipType()
        );
    }
}
