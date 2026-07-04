package nodrify.inc.octane.wellness.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodrify.inc.octane.wellness.domain.model.commands.MarkNotificationAsReadCommand;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllNotificationsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetNotificationsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.NotificationCommandService;
import nodrify.inc.octane.wellness.domain.services.NotificationQueryService;
import nodrify.inc.octane.wellness.interfaces.rest.resources.CreateNotificationResource;
import nodrify.inc.octane.wellness.interfaces.rest.resources.NotificationResource;
import nodrify.inc.octane.wellness.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import nodrify.inc.octane.wellness.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/notifications",produces = APPLICATION_JSON_VALUE)
@Tag(name="Notifications", description = "Operations related to notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationQueryService notificationQueryService;
    private final NotificationCommandService notificationCommandService;

    @PostMapping
    @Operation(summary = "Create a new notification", description = "Creates a new notification")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Notification created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Related entity not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<NotificationResource> createNotification(
            @RequestBody CreateNotificationResource resource){
        // Transform resource to command
        var createNotificationCommand = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);

        // Handle the command
        var notificationId = notificationCommandService.handle(createNotificationCommand);

        // Validate the creation
        if (notificationId == null) {
            return ResponseEntity.badRequest().build();
        }
        // Retrieve the created notification
        var createdNotification = notificationQueryService.handle(new GetNotificationByIdQuery(notificationId));

        if (createdNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var notificationEntity = createdNotification.get();

        // Transform to resource
        var notificationResource= NotificationResourceFromEntityAssembler.toResourceFromEntity(notificationEntity);

        return ResponseEntity.status(201).body(notificationResource);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieves a notification by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Notification not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long id){
        //Transform resource to query
        var getNotificationByIdQuery=new GetNotificationByIdQuery(id);
        //Handle the query
        var notificationOpt = notificationQueryService.handle( getNotificationByIdQuery );
        //Validate the retrieval
        if (notificationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var notificationEntity = notificationOpt.get();

        //Transform to resource
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notificationEntity);

        return ResponseEntity.status(200).body(notificationResource);


    }

    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieves all notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No notifications found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<NotificationResource>> getAllNotifications(){
        //Transform resource to query
        var getAllNotificationsQuery = new GetAllNotificationsQuery();
        //Handle the query
        var notifications = notificationQueryService.handle( getAllNotificationsQuery );

        //Transform to resources
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.status(200).body(notificationResources);

    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get notifications by vehicle ID", description = "Retrieves all notifications for a specific vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No notifications found for vehicle"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<NotificationResource>> getNotificationsByVehicleId(@PathVariable Long vehicleId){
        //Transform resource to query
        var getNotificationsByVehicleIdQuery = new GetNotificationsByVehicleIdQuery(vehicleId);
        //Handle the query
        var notifications = notificationQueryService.handle( getNotificationsByVehicleIdQuery );

        //Validate if notifications is empty for this vehicle
        if (notifications.isEmpty()) {
            return ResponseEntity.status(200).body(Collections.emptyList());
        }

        //Transform to resources
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.status(200).body(notificationResources);

    }

    @Operation(summary = "Mark notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResource> markNotificationAsRead(@PathVariable Long id) {
        try {
            // Transform de resource to command
            var command = new MarkNotificationAsReadCommand(id);
            // Handle the command
            var notification = notificationCommandService.handle(command);
            // Transform entity to resource and return response
            return notification.map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
