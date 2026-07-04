package nodrify.inc.octane.assignments.domain.model.queries;

public record GetAssignmentsByMechanicIdAndStatusQuery(
        Long mechanicId,
        String status
) {
}
