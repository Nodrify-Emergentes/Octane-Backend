package nodrify.inc.octane.wellness.domain.model.events;

import lombok.Getter;
import nodrify.inc.octane.wellness.domain.model.valueobjects.EnvironmentalConditions;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class EnvironmentalConditionAlertEvent extends ApplicationEvent {

    private final Long vehicleId; // Detect whose vehicle triggered the alert
    private final EnvironmentalConditions environmentalConditions; // Dates of temperature and humidity that triggered the alert
    private final String alertType; // "HIGH_TEMP", "LOW_TEMP", "HIGH_HUMIDITY", etc.  Classify alert for decision making
    private final String severity; // "LOW", "MEDIUM", "HIGH" Classify alert for decision making
    private final LocalDateTime registeredAt; //WHEN exactly did it happen?

    //Event's constructor
    public EnvironmentalConditionAlertEvent(Object source,
                                            Long vehicleId,
                                            EnvironmentalConditions environmentalConditions,
                                            String alertType,
                                            String severity) {
        super(source);
        this.vehicleId = vehicleId;
        this.environmentalConditions = environmentalConditions;
        this.alertType = alertType;
        this.severity = severity;
        this.registeredAt = LocalDateTime.now();
    }

    /**
     * Descripción detallada del evento
     */
    public String getDescription() {
        return String.format(
                "Environmental alert for vehicle %d - Type: %s, Severity: %s, Temp: %.1f°C",
                vehicleId,
                alertType,
                severity,
                environmentalConditions.temperatureCelsius()
        );
    }
}
