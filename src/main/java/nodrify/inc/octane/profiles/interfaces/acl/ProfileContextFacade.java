package nodrify.inc.octane.profiles.interfaces.acl;

public interface ProfileContextFacade {
    Long createProfile(String firstName, String lastName, String email, String photoUrl, Long userId);

    Long getProfileIdByUserId(Long userId);
}
