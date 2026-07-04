package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.entities.Notification;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllNotificationsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationsByVehicleIdQuery;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {

    Optional<Notification> handle(GetNotificationByIdQuery getNotificationByIdQuery);

    List<Notification> handle(GetAllNotificationsQuery getAllNotificationsQuery);

    List<Notification> handle(GetNotificationsByVehicleIdQuery getNotificationsByVehicleIdQuery);
}
