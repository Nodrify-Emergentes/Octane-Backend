package nodrify.inc.octane.wellness.domain.model.queries;

public record GetNotificationsByVehicleIdQuery(Long vehicleId) {
    public GetNotificationsByVehicleIdQuery {
        if (vehicleId == null || vehicleId <= 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or less than 1");
        }
    }
}
