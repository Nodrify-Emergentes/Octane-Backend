package nodrify.inc.octane.vehicles.interfaces.rest.transform;

import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.ModelResource;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.OwnerResource;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {
    public static VehicleResource toResourceFromEntity(Vehicle vehicle) {
        ModelResource model = ModelResourceFromEntityAssembler.toResourceFromEntity(vehicle.getModel());
        OwnerResource owner = OwnerResourceFromEntityAssembler.toResourceFromEntity(vehicle.getOwner());

        return new VehicleResource(
                vehicle.getId(),
                owner,
                model,
                vehicle.getPlate().plate(),
                vehicle.getYear().year()
        );
    }
}
