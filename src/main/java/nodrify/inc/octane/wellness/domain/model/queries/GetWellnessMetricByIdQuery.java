package nodrify.inc.octane.wellness.domain.model.queries;

public record GetWellnessMetricByIdQuery(Long wellnessMetricId) {
    public GetWellnessMetricByIdQuery{
        if (wellnessMetricId == null || wellnessMetricId <= 0) {
            throw new IllegalArgumentException("Wellness Metric ID must be a positive non-null value");
        }
    }
}
