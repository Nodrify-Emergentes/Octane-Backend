package nodrify.inc.octane.assignments.domain.services;

import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.assignments.domain.model.commands.CreateMechanicCommand;
import nodrify.inc.octane.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;

import java.util.Optional;

public interface MechanicCommandService {
    Optional<Mechanic> handle(CreateMechanicCommand command);

    Optional<Mechanic> handle(UpdateMechanicMembershipTypeCommand command);
}
