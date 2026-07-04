package nodrify.inc.octane.iam.interfaces.rest.resources;

import nodrify.inc.octane.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record UserResource(
        Long id,
        String username,
        List<Roles> roles
) { }
