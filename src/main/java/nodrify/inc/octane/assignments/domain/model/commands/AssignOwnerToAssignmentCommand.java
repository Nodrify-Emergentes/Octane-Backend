package nodrify.inc.octane.assignments.domain.model.commands;

public record AssignOwnerToAssignmentCommand(
    String assignmentCode,
    Long ownerId
) {
}
