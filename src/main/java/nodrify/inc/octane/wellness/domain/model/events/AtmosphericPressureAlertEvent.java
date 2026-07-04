package nodrify.inc.octane.wellness.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AtmosphericPressureAlertEvent extends ApplicationEvent {

    private final Long vehicleId;
    private final Float pressureHpa;
    private final String alertType; // "LOW_PRESSURE", "HIGH_PRESSURE"
    private final String severity; // "LOW", "MEDIUM", "HIGH"
    private final LocalDateTime registeredAt;

    public AtmosphericPressureAlertEvent(Object source,
                                         Long vehicleId,
                                         Float pressureHpa,
                                         String alertType,
                                         String severity) {
        super(source);
        this.vehicleId = vehicleId;
        this.pressureHpa = pressureHpa;
        this.alertType = alertType;
        this.severity = severity;
        this.registeredAt = LocalDateTime.now();
    }

    public String getDescription() {
        return String.format(
                "Atmospheric pressure alert for vehicle %d - Type: %s, Severity: %s, Pressure: %.1f hPa",
                vehicleId,
                alertType,
                severity,
                pressureHpa
        );
    }
}