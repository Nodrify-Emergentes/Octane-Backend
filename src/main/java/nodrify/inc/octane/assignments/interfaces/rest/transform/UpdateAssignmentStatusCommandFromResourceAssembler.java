package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.commands.UpdateAssignmentStatusCommand;
import nodrify.inc.octane.assignments.interfaces.rest.resources.UpdateAssignmentStatusResource;

public class UpdateAssignmentStatusCommandFromResourceAssembler {
    public static UpdateAssignmentStatusCommand updateAssignmentStatusCommandFromResourceAssembler(Long assignmentId, UpdateAssignmentStatusResource resource) {
        return new UpdateAssignmentStatusCommand(assignmentId, resource.status());
    }
}
