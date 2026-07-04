package nodrify.inc.octane.profiles.application.internal.queryservices;

import nodrify.inc.octane.profiles.application.internal.outboundservices.acl.ExternalIamService;
import nodrify.inc.octane.profiles.domain.model.aggregates.Profile;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByEmailQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByIdQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUserId;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUsernameQuery;
import nodrify.inc.octane.profiles.domain.model.valueobjects.UserId;
import nodrify.inc.octane.profiles.domain.services.ProfileQueryService;
import nodrify.inc.octane.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;
    private final ExternalIamService externalIamService;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository, ExternalIamService externalIamService) {
        this.profileRepository = profileRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmailAddress(query.emailAddress());
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserId query) {
        return profileRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Profile> handle(GetProfileByUsernameQuery query) {
        var user = this.externalIamService.getUserByUsername(query.username());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        UserId userId = new UserId(user.get().getId());
        return profileRepository.findByUserId(userId);
    }
}
