package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.commands.CreateNotificationCommand;
import nodrify.inc.octane.wellness.domain.model.commands.MarkNotificationAsReadCommand;
import nodrify.inc.octane.wellness.domain.model.entities.Notification;

import java.util.Optional;

public interface NotificationCommandService {

    Long handle(CreateNotificationCommand createNotificationCommand);

    Optional<Notification> handle(MarkNotificationAsReadCommand markNotificationAsReadCommand);
}
