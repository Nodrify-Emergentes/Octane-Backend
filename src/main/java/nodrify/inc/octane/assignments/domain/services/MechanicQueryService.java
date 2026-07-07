package nodrify.inc.octane.assignments.domain.services;

import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.assignments.domain.model.queries.GetMechanicByIdQuery;

import java.util.Optional;

public interface MechanicQueryService {
    Optional<Mechanic> handle(GetMechanicByIdQuery query);
}
