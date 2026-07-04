package nodrify.inc.octane.wellness.domain.model.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nodrify.inc.octane.wellness.domain.model.commands.CreateNotificationCommand;

import java.time.LocalDateTime;

@Getter
@Entity
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    private Long vehicleId;
    private String title;
    private String message;
    private String type; // "ENVIRONMENTAL_ALERT", "SYSTEM", etc.
    private String severity; // "LOW", "MEDIUM", "HIGH"
    private boolean read;
    private LocalDateTime occurredAt;

    protected Notification() {
        super();// Constructor protegido para JPA
    }

    public Notification(CreateNotificationCommand command) {
        this.vehicleId = command.vehicleId();
        this.title = command.title();
        this.message = command.message();
        this.type = command.type();
        this.severity = command.severity();
        this.read = false;
        this.occurredAt =  LocalDateTime.now();
    }

    public void markAsRead() {
        this.read = true;
    }

    public boolean isUnread() {
        return !read;
    }

    public boolean getRead() {
        return  read;
    }
}
