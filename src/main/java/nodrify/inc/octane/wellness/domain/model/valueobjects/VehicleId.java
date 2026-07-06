package nodrify.inc.octane.wellness.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleId(Long vehicleId) {
    public VehicleId {
        if (vehicleId == null || vehicleId < 1) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or less than 1");
        }
    }
}
