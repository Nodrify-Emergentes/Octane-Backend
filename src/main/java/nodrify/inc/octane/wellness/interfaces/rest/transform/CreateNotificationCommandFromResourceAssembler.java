package nodrify.inc.octane.wellness.interfaces.rest.transform;

import nodrify.inc.octane.wellness.domain.model.commands.CreateNotificationCommand;
import nodrify.inc.octane.wellness.interfaces.rest.resources.CreateNotificationResource;

import java.time.LocalDateTime;

public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource createNotificationResource) {
        return new CreateNotificationCommand(
                createNotificationResource.vehicleId(),
                createNotificationResource.title(),
                createNotificationResource.message(),
                createNotificationResource.type(),
                createNotificationResource.severity(),
                LocalDateTime.now() // Set occurredAt to current time
        );
    }
}
