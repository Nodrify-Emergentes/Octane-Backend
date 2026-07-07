package nodrify.inc.octane.wellness.interfaces.rest.resources;

import java.time.LocalDateTime;

public record WellnessMetricResource(
        Long id,

        Long vehicleId,

        Float latitude,
        Float longitude,

        Double CO2Ppm,
        Double NH3Ppm,
        Double BenzenePpm,

        Float temperatureCelsius,

        Float pressureHpa,

        Boolean impactDetected,

        LocalDateTime registeredAt
) {

}
