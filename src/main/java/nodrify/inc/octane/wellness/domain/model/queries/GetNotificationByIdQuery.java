package nodrify.inc.octane.wellness.domain.model.queries;

public record GetNotificationByIdQuery(Long notificationId) {
    public GetNotificationByIdQuery {
        if (notificationId == null || notificationId <= 0) {
            throw new IllegalArgumentException("Notification ID must be a positive non-null value");
        }
    }
}
