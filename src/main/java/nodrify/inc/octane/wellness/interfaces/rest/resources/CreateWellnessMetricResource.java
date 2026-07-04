package nodrify.inc.octane.wellness.interfaces.rest.resources;

public record CreateWellnessMetricResource(

        Long vehicleId,

        Float latitude,
        Float longitude,

        Double CO2Ppm,
        Double NH3Ppm,
        Double BenzenePpm,

        Float temperatureCelsius,

        Float pressureHpa,

        Boolean impactDetected
) {
}
