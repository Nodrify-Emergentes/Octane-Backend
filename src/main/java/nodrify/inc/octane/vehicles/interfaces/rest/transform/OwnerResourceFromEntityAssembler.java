package nodrify.inc.octane.vehicles.interfaces.rest.transform;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.OwnerResource;

public class OwnerResourceFromEntityAssembler {

    public static OwnerResource toResourceFromEntity(Owner entity) {
        return new OwnerResource(
                entity.getId(),
                entity.getProfile().getCompleteName()
        );
    }
}
