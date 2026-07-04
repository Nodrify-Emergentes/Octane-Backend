package nodrify.inc.octane.wellness.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class StatusImpactAlertEvent extends ApplicationEvent {

    private final Long vehicleId;
    private final Boolean impactDetected;
    private final String alertType; // "IMPACT_DETECTED"
    private final String severity; // "HIGH" (siempre alta para impactos)
    private final LocalDateTime registeredAt;

    public StatusImpactAlertEvent(Object source,
                                  Long vehicleId,
                                  Boolean impactDetected,
                                  String alertType) {
        super(source);
        this.vehicleId = vehicleId;
        this.impactDetected = impactDetected;
        this.alertType = alertType;
        this.severity = "HIGH"; // Los impactos siempre son de alta severidad
        this.registeredAt = LocalDateTime.now();
    }

    public String getDescription() {
        return String.format(
                "Impact alert for vehicle %d - Impact detected: %s",
                vehicleId,
                impactDetected ? "YES" : "NO"
        );
    }
}
