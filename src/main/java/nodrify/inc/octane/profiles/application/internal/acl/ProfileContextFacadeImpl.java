package nodrify.inc.octane.profiles.application.internal.acl;

import nodrify.inc.octane.profiles.domain.model.commands.CreateProfileCommand;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUserId;
import nodrify.inc.octane.profiles.domain.model.valueobjects.UserId;
import nodrify.inc.octane.profiles.domain.services.ProfileCommandService;
import nodrify.inc.octane.profiles.domain.services.ProfileQueryService;
import nodrify.inc.octane.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfileContextFacadeImpl(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @Override
    public Long createProfile(String firstName, String lastName, String email, String photoUrl, Long userId) {
        var createProfileCommand = new CreateProfileCommand(firstName, lastName, email, photoUrl, userId);
        var profile = profileCommandService.handle(createProfileCommand);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }

    @Override
    public Long getProfileIdByUserId(Long userId) {
        UserId user = new UserId(userId);
        var getProfileByUserId = new GetProfileByUserId(user);

        var profile = profileQueryService.handle(getProfileByUserId);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }
}
