package nodrify.inc.octane.vehicles.domain.services;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.commands.AddVehicleToOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.CreateOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.DeleteVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.UpdateVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;

import java.util.Optional;

public interface OwnerCommandService {
    Optional<Owner> handle(CreateOwnerCommand command);

    Optional<Vehicle> handle(AddVehicleToOwnerCommand command);

    Optional<Vehicle> handle(UpdateVehicleFromOwnerCommand command);

    void handle(DeleteVehicleFromOwnerCommand command);
}
