package nodrify.inc.octane.wellness.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AtmosphericPressure(Float pressureHpa) {
    public AtmosphericPressure {
        if (pressureHpa == null || pressureHpa < 300.0f || pressureHpa > 1100.0f) {
            throw new IllegalArgumentException("Atmospheric pressure must be between 300 and 1100 hPa");
        }
     }

}
