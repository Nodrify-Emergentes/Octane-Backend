package nodrify.inc.octane.wellness.interfaces.rest.transform;

import nodrify.inc.octane.wellness.domain.model.commands.UpdateWellnessMetricCommand;
import nodrify.inc.octane.wellness.interfaces.rest.resources.UpdateWellnessMetricResource;

public class UpdateWellnessMetricCommandFromResourceAssembler {
    public static UpdateWellnessMetricCommand toCommandFromResource(UpdateWellnessMetricResource resource, Long wellnessMetricId) {
        return new UpdateWellnessMetricCommand(
                wellnessMetricId,

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
