package nodrify.inc.octane.profiles.domain.services;

import nodrify.inc.octane.profiles.domain.model.aggregates.Profile;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByEmailQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByIdQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUserId;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUsernameQuery;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    Optional<Profile> handle(GetProfileByEmailQuery query);
    Optional<Profile> handle(GetProfileByUserId query);
    Optional<Profile> handle(GetProfileByUsernameQuery query);
}
