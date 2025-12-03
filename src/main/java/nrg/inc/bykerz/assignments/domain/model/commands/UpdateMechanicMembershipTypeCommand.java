package nrg.inc.bykerz.assignments.domain.model.commands;

import nrg.inc.bykerz.assignments.domain.model.valueobjects.MembershipType;

public record UpdateMechanicMembershipTypeCommand(Long mechanicId, MembershipType membershipType) {
}
