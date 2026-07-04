package nodrify.inc.octane.profiles.interfaces.rest.resources;

public record CreateProfileResource(
        String firstName,
        String lastName,
        String email,
        String photoUrl,
        Long userId
) {
    public CreateProfileResource {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (photoUrl == null || photoUrl.isBlank()) throw new IllegalArgumentException("PhotoUrl is required");
        if (userId == null) throw new IllegalArgumentException("User ID is required");
    }
}
