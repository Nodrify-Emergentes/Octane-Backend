package nodrify.inc.octane.wellness.application.internal.eventhandlers;

import lombok.RequiredArgsConstructor;
import nodrify.inc.octane.wellness.domain.model.commands.CreateNotificationCommand;
import nodrify.inc.octane.wellness.domain.model.events.AirQualityAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.AtmosphericPressureAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.EnvironmentalConditionAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.StatusImpactAlertEvent;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationByIdQuery;
import nodrify.inc.octane.wellness.domain.services.NotificationCommandService;
import nodrify.inc.octane.wellness.domain.services.NotificationQueryService;
import nodrify.inc.octane.wellness.interfaces.websocket.WellnessWebSocketController;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WellnessAlertEventHandler {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final WellnessWebSocketController webSocketController;

    @EventListener(AirQualityAlertEvent.class)
    public void on(AirQualityAlertEvent event) {
        CreateNotificationCommand command = new CreateNotificationCommand(
                event.getVehicleId(),
                "Air Quality Alert - " + event.getAlertType(),
                event.getDescription(),
                "AIR_QUALITY_ALERT",
                event.getSeverity(),
                event.getRegisteredAt()
        );

        var notificationId = notificationCommandService.handle(command);
        sendNotificationViaWebSocket(event.getVehicleId(), notificationId);

        System.out.println("Air quality notification created: " + event.getDescription());
    }

    @EventListener(AtmosphericPressureAlertEvent.class)
    public void on(AtmosphericPressureAlertEvent event) {
        CreateNotificationCommand command = new CreateNotificationCommand(
                event.getVehicleId(),
                "Pressure Alert - " + event.getAlertType(),
                event.getDescription(),
                "PRESSURE_ALERT",
                event.getSeverity(),
                event.getRegisteredAt()
        );

        Long notificationId=notificationCommandService.handle(command);
        sendNotificationViaWebSocket(event.getVehicleId(), notificationId);
        System.out.println("Pressure notification created: " + event.getDescription());
    }

    @EventListener(EnvironmentalConditionAlertEvent.class)
    public void on(EnvironmentalConditionAlertEvent event) {
        CreateNotificationCommand command = new CreateNotificationCommand(
                event.getVehicleId(),
                "Environmental Alert - " + event.getAlertType(),
                event.getDescription(),
                "ENVIRONMENTAL_ALERT",
                event.getSeverity(),
                event.getRegisteredAt()
        );

        Long notificationId = notificationCommandService.handle(command);
        sendNotificationViaWebSocket(event.getVehicleId(), notificationId);
        System.out.println("Environmental notification created: " + event.getDescription());
    }

    @EventListener(StatusImpactAlertEvent.class)
    public void on(StatusImpactAlertEvent event) {
        CreateNotificationCommand command = new CreateNotificationCommand(
                event.getVehicleId(),
                "Impact Alert",
                event.getDescription(),
                "IMPACT_ALERT",
                event.getSeverity(),
                event.getRegisteredAt()
        );

        Long notificationId = notificationCommandService.handle(command);
        sendNotificationViaWebSocket(event.getVehicleId(), notificationId);
        System.out.println("Impact notification created: " + event.getDescription());
    }


    private void sendNotificationViaWebSocket(Long vehicleId, Long notificationId) {
        var notificationOpt = notificationQueryService.handle(new GetNotificationByIdQuery(notificationId));

        if (notificationOpt.isPresent()) {
            try {
                var notification = notificationOpt.get();
                // Enviar al vehículo específico
                webSocketController.sendNotificationToVehicle(vehicleId, notification);
                // También enviar por tipo de alerta
                webSocketController.sendAlertByType("AIR_QUALITY", notification);
            } catch (Exception e) {
                System.err.println("Failed to send WebSocket alert for vehicle " + vehicleId + ": " + e.getMessage());
            }
        }
    }

}
