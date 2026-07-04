package nodrify.inc.octane.vehicles.domain.services;


import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByPlateQuery;

import java.util.Optional;

public interface VehiclesQueryService {

    Optional<Vehicle> handle(GetVehicleByIdQuery query);

    Optional<Vehicle> handle(GetVehicleByPlateQuery query);

}
