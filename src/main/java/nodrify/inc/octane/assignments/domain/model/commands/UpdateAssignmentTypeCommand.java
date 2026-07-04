package nodrify.inc.octane.assignments.domain.model.commands;

public record UpdateAssignmentTypeCommand(
        Long assignmentId,
        String assignmentType
) {
}
