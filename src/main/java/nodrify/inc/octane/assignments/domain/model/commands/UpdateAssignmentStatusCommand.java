package nodrify.inc.octane.assignments.domain.model.commands;

public record UpdateAssignmentStatusCommand(
        Long assignmentId,
        String assignmentStatus
) {
}
