package nodrify.inc.octane.wellness.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerId(Long ownerId) {
    public OwnerId {
        if (ownerId == null || ownerId < 1) {
            throw new IllegalArgumentException("Owner ID cannot be null or less than 1");
        }
    }
}
