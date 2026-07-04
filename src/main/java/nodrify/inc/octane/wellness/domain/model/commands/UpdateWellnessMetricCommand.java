package nodrify.inc.octane.wellness.domain.model.commands;

public record UpdateWellnessMetricCommand(
        Long wellnessMetricId,

        Float latitude,
        Float longitude,

        Double CO2Ppm,
        Double NH3Ppm,
        Double BenzenePpm,

        Float temperatureCelsius,

        Float pressureHpa,

        Boolean impactDetected
) {
    public UpdateWellnessMetricCommand {
        if (wellnessMetricId == null || wellnessMetricId <=0) {
            throw new IllegalArgumentException("Wellness Metric ID must be a positive non-zero value.");
        }
    }
}
