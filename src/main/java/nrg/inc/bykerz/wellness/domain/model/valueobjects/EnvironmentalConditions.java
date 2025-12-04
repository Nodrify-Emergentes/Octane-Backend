package nrg.inc.bykerz.wellness.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record EnvironmentalConditions(Float temperatureCelsius) {
    public EnvironmentalConditions{
        if (temperatureCelsius < -50 || temperatureCelsius > 60) {
            throw new IllegalArgumentException("Temperature must be between -50 and 60 Celsius");
        }
    }
}
