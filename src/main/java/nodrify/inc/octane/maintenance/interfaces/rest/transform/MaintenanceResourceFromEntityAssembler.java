package nodrify.inc.octane.maintenance.interfaces.rest.transform;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import nodrify.inc.octane.maintenance.interfaces.rest.resources.MaintenanceResource;

public class MaintenanceResourceFromEntityAssembler {
    public static MaintenanceResource toResourceFromEntity(Maintenance entity){
        return new MaintenanceResource(
                entity.getId(),
                entity.getDetails(),
                entity.getVehicleId(),
                entity.getDateOfService().toString(),
                entity.getLocation(),
                entity.getDescription(),
                entity.getState().getName().toString(),
                entity.getMaintenanceExpense() != null ?
                        ExpenseResourceFromEntityAssembler.toResourceFromEntity(entity.getMaintenanceExpense())
                        : null,
                entity.getMechanicId()
        );
    }
}
