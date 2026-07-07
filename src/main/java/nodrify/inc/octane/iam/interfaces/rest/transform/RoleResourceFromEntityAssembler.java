package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.entities.Role;
import nodrify.inc.octane.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role roleEntity) {

        return new RoleResource(
                roleEntity.getId(),
                roleEntity.getStringName()
        );
    }
}
