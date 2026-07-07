package nodrify.inc.octane.wellness.interfaces.rest.resources;

import java.time.LocalDateTime;

public record NotificationResource(
        Long id,

        Long vehicleId,
        String title,
        String message,
        String type, // "ENVIRONMENTAL_ALERT", "SYSTEM", etc.
        String severity, // "LOW", "MEDIUM", "HIGH"
        boolean read,
        LocalDateTime occurredAt
) {
}
