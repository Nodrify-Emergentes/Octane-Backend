package nodrify.inc.octane.wellness.domain.model.events;

import lombok.Getter;
import nodrify.inc.octane.wellness.domain.model.valueobjects.AirQuality;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AirQualityAlertEvent extends ApplicationEvent {

    private final Long vehicleId;
    private final AirQuality airQuality;
    private final String alertType; // "HIGH_CO2", "HIGH_NH3", "HIGH_BENZENE"
    private final String severity; // "LOW", "MEDIUM", "HIGH"
    private final LocalDateTime registeredAt;

    public AirQualityAlertEvent(Object source,
                                Long vehicleId,
                                AirQuality airQuality,
                                String alertType,
                                String severity) {
        super(source);
        this.vehicleId = vehicleId;
        this.airQuality = airQuality;
        this.alertType = alertType;
        this.severity = severity;
        this.registeredAt = LocalDateTime.now();
    }

    public String getDescription() {
        return String.format(
                "Air quality alert for vehicle %d - Type: %s, Severity: %s, CO2: %.2f ppm, NH3: %.2f ppm, Benzene: %.2f ppm",
                vehicleId,
                alertType,
                severity,
                airQuality.CO2Ppm(),
                airQuality.NH3Ppm(),
                airQuality.BenzenePpm()
        );
    }
}
