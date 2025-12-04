package nrg.inc.bykerz.wellness.interfaces.rest.transform;

import nrg.inc.bykerz.wellness.domain.model.commands.CreateWellnessMetricCommand;
import nrg.inc.bykerz.wellness.interfaces.rest.resources.CreateWellnessMetricResource;

public class CreateWellnessMetricCommandFromResourceAssembler {
    public static CreateWellnessMetricCommand toCommandFromResource(CreateWellnessMetricResource resource) {
        return new CreateWellnessMetricCommand(
                resource.vehicleId(),

                resource.latitude(),
                resource.longitude(),

                resource.CO2Ppm(),
                resource.NH3Ppm(),
                resource.BenzenePpm(),

                resource.temperatureCelsius(),

                resource.pressureHpa(),

                resource.impactDetected()
        );
    }
}
