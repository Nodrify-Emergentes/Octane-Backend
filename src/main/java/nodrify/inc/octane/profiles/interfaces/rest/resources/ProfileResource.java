package nodrify.inc.octane.profiles.interfaces.rest.resources;

public record ProfileResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        String photoUrl,
        Long userId
) {
}