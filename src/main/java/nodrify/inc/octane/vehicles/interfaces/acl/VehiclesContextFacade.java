package nodrify.inc.octane.vehicles.interfaces.acl;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehiclesContextFacade {

    Optional<Owner> fetchOwnerById(Long ownerId);

    List<Vehicle> fetchVehiclesByOwnerId(Long ownerId);

    Optional<Vehicle> fetchVehicleById(Long vehicleId);

    Long createOwner(Long profileId);

}
