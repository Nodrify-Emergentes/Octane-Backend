package nodrify.inc.octane.shared.application.internal.outboundservices.acl;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.interfaces.acl.VehiclesContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalVehiclesService {
    private final VehiclesContextFacade vehiclesContextFacade;

    public ExternalVehiclesService(VehiclesContextFacade vehiclesContextFacade) {
        this.vehiclesContextFacade = vehiclesContextFacade;
    }

    public Optional<Owner> getOwnerById(Long ownerId) {
        var owner = this.vehiclesContextFacade.fetchOwnerById(ownerId);
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner not found for ownerId: " + ownerId);
        }
        return owner;
    }

    public Optional<Vehicle> fetchVehicleById(Long vehicleId){
        try{
            return vehiclesContextFacade.fetchVehicleById(vehicleId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> fetchVehiclesByOwnerId(Long ownerId) {
        try {
            return vehiclesContextFacade.fetchVehiclesByOwnerId(ownerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long createOwner(Long profileId) {
        return vehiclesContextFacade.createOwner(profileId);
    }
}
