package nodrify.inc.octane.iam.interfaces.rest.resources;

import nodrify.inc.octane.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record SignUpResource(
        String firstName,
        String lastName,
        String username,
        String email,
        String photoUrl,
        String password,
        List<Roles> roles
) {
}
