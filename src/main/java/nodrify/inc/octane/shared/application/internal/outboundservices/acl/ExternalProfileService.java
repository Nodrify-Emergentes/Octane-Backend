package nodrify.inc.octane.shared.application.internal.outboundservices.acl;

import nodrify.inc.octane.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalProfileService {
    private final ProfileContextFacade profileContextFacade;

    public ExternalProfileService(ProfileContextFacade profileContextFacade) {
        this.profileContextFacade = profileContextFacade;
    }

    public Long createProfile(String firstName, String lastName, String email, String photoUrl, Long userId) {
        return profileContextFacade.createProfile(firstName, lastName, email, photoUrl, userId);
    }

    public Long getProfileIdByUserId(Long userId) {
        return profileContextFacade.getProfileIdByUserId(userId);
    }


}
