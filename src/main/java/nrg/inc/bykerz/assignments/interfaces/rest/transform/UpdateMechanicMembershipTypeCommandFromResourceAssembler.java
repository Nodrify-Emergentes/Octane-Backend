package nrg.inc.bykerz.assignments.interfaces.rest.transform;

import nrg.inc.bykerz.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;
import nrg.inc.bykerz.assignments.interfaces.rest.resources.UpdateMechanicMembershipTypeResource;

public class UpdateMechanicMembershipTypeCommandFromResourceAssembler {
    public static UpdateMechanicMembershipTypeCommand toCommandFromResource(Long mechanicId,UpdateMechanicMembershipTypeResource resource){
        return new UpdateMechanicMembershipTypeCommand(
                mechanicId,
                resource.membershipType()
        );
    }
}
