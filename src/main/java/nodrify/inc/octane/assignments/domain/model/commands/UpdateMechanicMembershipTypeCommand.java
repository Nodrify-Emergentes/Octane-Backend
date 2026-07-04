package nodrify.inc.octane.assignments.domain.model.commands;

import nodrify.inc.octane.assignments.domain.model.valueobjects.MembershipType;

public record UpdateMechanicMembershipTypeCommand(Long mechanicId, MembershipType membershipType) {
}
