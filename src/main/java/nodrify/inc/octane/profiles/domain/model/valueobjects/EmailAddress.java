package nodrify.inc.octane.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;

@Embeddable
public record EmailAddress(@Email String email) {
    public EmailAddress() {
        this(null);
    }
}
