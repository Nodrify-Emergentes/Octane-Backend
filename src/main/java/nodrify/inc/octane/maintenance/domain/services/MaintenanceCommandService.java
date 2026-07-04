package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import nodrify.inc.octane.maintenance.domain.model.commands.AssignExpenseToMaintenanceCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.CreateMaintenanceCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.DeleteMaintenanceCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.UpdateStateOfMaintenanceByIdCommand;

import java.util.Optional;

public interface MaintenanceCommandService {
    Optional<Maintenance> handle(CreateMaintenanceCommand command);
    void handle(DeleteMaintenanceCommand command);
    Optional<Maintenance> handle(AssignExpenseToMaintenanceCommand command);
    Optional<Maintenance> handle(UpdateStateOfMaintenanceByIdCommand command);
}
