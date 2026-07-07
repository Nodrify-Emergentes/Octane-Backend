package nodrify.inc.octane.wellness.application.internal.commandservices;

import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.wellness.domain.model.commands.CreateNotificationCommand;
import nodrify.inc.octane.wellness.domain.model.commands.MarkNotificationAsReadCommand;
import nodrify.inc.octane.wellness.domain.model.entities.Notification;
import nodrify.inc.octane.wellness.domain.services.NotificationCommandService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;
    private final ExternalVehiclesService externalVehicleService;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository, ExternalVehiclesService externalVehicleService) {
        this.externalVehicleService = externalVehicleService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Long handle(CreateNotificationCommand createNotificationCommand) {

        //Validate the vehicle exists
        var vehicleOpt =externalVehicleService.fetchVehicleById(createNotificationCommand.vehicleId());

        if(vehicleOpt.isEmpty()){
            throw new IllegalArgumentException( "Vehicle with id " + createNotificationCommand.vehicleId() + " does not exist");
        }

        //Create the notification
        var notification = new Notification(createNotificationCommand);

        try{
            notificationRepository.save(notification);
            return notification.getId();
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Optional<Notification> handle(MarkNotificationAsReadCommand command) {
        var notificationOpt = notificationRepository.findById(command.notificationId());
        if (notificationOpt.isPresent()) {
            notificationOpt.get().markAsRead();
            notificationRepository.save(notificationOpt.get());
            return notificationOpt;
        }
        return Optional.empty();
    }
}
