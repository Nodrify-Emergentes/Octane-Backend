package nodrify.inc.octane.wellness.application.internal.queryservices;

import nodrify.inc.octane.wellness.domain.model.entities.Notification;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllNotificationsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.NotificationQueryService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery getNotificationByIdQuery) {
        return notificationRepository.findById(getNotificationByIdQuery.notificationId());
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery getAllNotificationsQuery) {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> handle(GetNotificationsByVehicleIdQuery getNotificationsByVehicleIdQuery) {
        return notificationRepository.findByVehicleId(getNotificationsByVehicleIdQuery.vehicleId());
    }
}
