package nodrify.inc.octane.profiles.domain.model.queries;

import nodrify.inc.octane.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
