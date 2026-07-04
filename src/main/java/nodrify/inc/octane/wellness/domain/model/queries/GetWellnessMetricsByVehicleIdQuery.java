package nodrify.inc.octane.wellness.domain.model.queries;

public record GetWellnessMetricsByVehicleIdQuery(Long vehicleId) {
    public GetWellnessMetricsByVehicleIdQuery{
        if(vehicleId==null||vehicleId <= 0){
            throw new IllegalArgumentException("Vehicle ID must be a positive non-null value");
        }
    }
}
