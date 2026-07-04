package nodrify.inc.octane.assignments.interfaces.rest.transform;

import nodrify.inc.octane.assignments.domain.model.commands.UpdateAssignmentTypeCommand;
import nodrify.inc.octane.assignments.interfaces.rest.resources.UpdateAssignmentTypeResource;

public class UpdateAssignmentTypeCommandFromResourceAssembler {
    public static UpdateAssignmentTypeCommand updateAssignmentTypeCommandFromResourceAssembler(Long assignmentId, UpdateAssignmentTypeResource resource) {
        return new UpdateAssignmentTypeCommand(assignmentId, resource.type());
    }
}
