package nodrify.inc.octane.wellness.interfaces.rest.transform;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.interfaces.rest.resources.WellnessMetricResource;

public class WellnessMetricResourceFromEntityAssembler {
    public static WellnessMetricResource toResourceFromEntity(WellnessMetric entity) {
        return new WellnessMetricResource(
                entity.getId(),

                entity.getVehicleId(),

                entity.getCoordinates().latitude(),
                entity.getCoordinates().longitude(),

                entity.getAirQuality().CO2Ppm(),
                entity.getAirQuality().NH3Ppm(),
                entity.getAirQuality().BenzenePpm(),

                entity.getEnvironmentalConditions().temperatureCelsius(),

                entity.getAtmosphericPressure().pressureHpa(),

                entity.getStatusImpact().impactDetected(),
                entity.getRegisteredAt()
        );
    }
}
