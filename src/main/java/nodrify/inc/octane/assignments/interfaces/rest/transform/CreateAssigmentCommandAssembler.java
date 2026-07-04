package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.commands.CreateAssignmentCommand;

public class CreateAssigmentCommandAssembler {
    public static CreateAssignmentCommand toCommand(Long mechanicId) {
        return new CreateAssignmentCommand(
                mechanicId,
                "PENDING",
                "UNCATEGORIZED"
        );
    }
}
