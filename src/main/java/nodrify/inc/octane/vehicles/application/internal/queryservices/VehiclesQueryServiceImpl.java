package nodrify.inc.octane.vehicles.application.internal.queryservices;

import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetVehicleByPlateQuery;
import nodrify.inc.octane.vehicles.domain.model.valueobjects.Plate;
import nodrify.inc.octane.vehicles.domain.services.VehiclesQueryService;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.VehicleReadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiclesQueryServiceImpl implements VehiclesQueryService {

    final private VehicleReadRepository vehicleReadRepository;

    public VehiclesQueryServiceImpl(VehicleReadRepository vehicleReadRepository) {
        this.vehicleReadRepository = vehicleReadRepository;
    }


    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleReadRepository.findById(query.vehicleId());
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByPlateQuery query) {
        return vehicleReadRepository.findByPlate(new Plate(query.plate()));
    }
}
