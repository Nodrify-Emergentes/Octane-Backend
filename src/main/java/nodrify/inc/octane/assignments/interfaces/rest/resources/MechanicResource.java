package nodrify.inc.octane.assignments.interfaces.rest.resources;

import nodrify.inc.octane.assignments.domain.model.valueobjects.MembershipType;

public record MechanicResource(
        Long mechanicId,
        String completeName,
        MembershipType membershipType
) {
}
