package nodrify.inc.octane.maintenance.domain.model.commands;

public record UpdateStateOfMaintenanceByIdCommand(String state, Long maintenanceId) {
}
