package nodrify.inc.octane.wellness.interfaces.rest.transform;

import nodrify.inc.octane.wellness.domain.model.entities.Notification;
import nodrify.inc.octane.wellness.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getVehicleId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType(),
                entity.getSeverity(),
                entity.getRead(),
                entity.getOccurredAt()
        );
    }
}
