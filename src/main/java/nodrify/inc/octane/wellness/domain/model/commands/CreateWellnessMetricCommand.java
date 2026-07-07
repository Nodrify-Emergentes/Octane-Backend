package nodrify.inc.octane.wellness.domain.model.commands;

public record CreateWellnessMetricCommand(
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
    public CreateWellnessMetricCommand{
        if (vehicleId == null || vehicleId<=0) {
            throw new IllegalArgumentException("vehicleId cannot be null, less than or equal to zero");
        }

    }
}
