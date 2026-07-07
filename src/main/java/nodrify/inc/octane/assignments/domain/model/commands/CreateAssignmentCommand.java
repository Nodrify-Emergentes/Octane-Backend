package nodrify.inc.octane.assignments.domain.model.commands;

public record CreateAssignmentCommand(
        Long mechanicId,
        String assignmentStatus,
        String assignmentType
) {
}
