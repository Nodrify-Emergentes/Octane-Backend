package nodrify.inc.octane.vehicles.domain.services;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllOwnersQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetOwnerByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetOwnerByVehicleIdQuery;

import java.util.List;
import java.util.Optional;

public interface OwnerQueryService {
    Optional<Owner> handle(GetOwnerByIdQuery query);
    Optional<Owner> handle(GetOwnerByVehicleIdQuery query);
    List<Owner> handle(GetAllOwnersQuery query);

}
