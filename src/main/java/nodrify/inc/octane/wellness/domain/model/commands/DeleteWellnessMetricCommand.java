package nodrify.inc.octane.wellness.domain.model.commands;

public record DeleteWellnessMetricCommand(Long wellnessMetricId) {
    public DeleteWellnessMetricCommand {
        if (wellnessMetricId == null || wellnessMetricId <= 0) {
            throw new IllegalArgumentException("Wellness Metric ID cannot be null or less than 1");
        }
    }
}
