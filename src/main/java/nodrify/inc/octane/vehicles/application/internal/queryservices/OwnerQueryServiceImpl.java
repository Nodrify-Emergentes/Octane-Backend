package nodrify.inc.octane.vehicles.application.internal.queryservices;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllOwnersQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetOwnerByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetOwnerByVehicleIdQuery;
import nodrify.inc.octane.vehicles.domain.services.OwnerQueryService;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final OwnerRepository ownerRepository;

    public OwnerQueryServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Optional<Owner> handle(GetOwnerByIdQuery query) {
        return this.ownerRepository.findById(query.ownerId());
    }

    @Override
    public Optional<Owner> handle(GetOwnerByVehicleIdQuery query) {
        return this.ownerRepository.findOwnerByVehicles_Id(query.vehicleId());
    }

    @Override
    public List<Owner> handle(GetAllOwnersQuery query) {
        return this.ownerRepository.findAll();
    }
}
