package nodrify.inc.octane.vehicles.interfaces.rest.transform;

import nodrify.inc.octane.vehicles.domain.model.commands.CreateModelCommand;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.CreateModelResource;

public class CreateModelCommandFromResourceAssembler {

    public static CreateModelCommand toCommandFromResource(CreateModelResource resource) {
        return new CreateModelCommand(
                resource.name(),
                resource.brand(),
                resource.modelYear(),
                resource.originCountry(),
                resource.producedAt(),
                resource.type(),
                resource.displacement(),
                resource.potency(),
                resource.engineType(),
                resource.engineTorque(),
                resource.weight(),
                resource.transmission(),
                resource.brakes(),
                resource.tank(),
                resource.seatHeight(),
                resource.consumption(),
                resource.price(),
                resource.oilCapacity(),
                resource.connectivity(),
                resource.durability(),
                resource.octane()
        );
    }
}
