package nodrify.inc.octane.maintenance.domain.model.commands;

public record AssignExpenseToMaintenanceCommand(Long maintenanceId, Long expenseId) {
}
