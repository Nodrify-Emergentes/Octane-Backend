package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.commands.SeedMaintenanceStatesCommand;

public interface MaintenanceStateCommandService {
    void handle(SeedMaintenanceStatesCommand command);
}
