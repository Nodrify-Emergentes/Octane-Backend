package nodrify.inc.octane.profiles.interfaces.rest.transform;

import nodrify.inc.octane.profiles.domain.model.aggregates.Profile;
import nodrify.inc.octane.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailAddress(),
                entity.getPhotoUrl(),
                entity.getUserId()
        );
    }
}
