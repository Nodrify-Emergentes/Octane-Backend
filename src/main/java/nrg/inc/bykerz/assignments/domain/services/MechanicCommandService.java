package nrg.inc.bykerz.assignments.domain.services;

import nrg.inc.bykerz.assignments.domain.model.aggregates.Mechanic;
import nrg.inc.bykerz.assignments.domain.model.commands.CreateMechanicCommand;
import nrg.inc.bykerz.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;

import java.util.Optional;

public interface MechanicCommandService {
    Optional<Mechanic> handle(CreateMechanicCommand command);

    Optional<Mechanic> handle(UpdateMechanicMembershipTypeCommand command);
}
