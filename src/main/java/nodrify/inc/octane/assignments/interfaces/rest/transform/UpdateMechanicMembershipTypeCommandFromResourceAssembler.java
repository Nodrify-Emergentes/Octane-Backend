package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;
import nodrify.inc.octane.assignments.interfaces.rest.resources.UpdateMechanicMembershipTypeResource;

public class UpdateMechanicMembershipTypeCommandFromResourceAssembler {
    public static UpdateMechanicMembershipTypeCommand toCommandFromResource(Long mechanicId,UpdateMechanicMembershipTypeResource resource){
        return new UpdateMechanicMembershipTypeCommand(
                mechanicId,
                resource.membershipType()
        );
    }
}
