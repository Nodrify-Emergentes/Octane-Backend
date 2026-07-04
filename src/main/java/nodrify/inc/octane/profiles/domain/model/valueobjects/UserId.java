package nodrify.inc.octane.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long userId) {
    public UserId {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("User ID cannot be null or less than 1");
        }
    }
}
