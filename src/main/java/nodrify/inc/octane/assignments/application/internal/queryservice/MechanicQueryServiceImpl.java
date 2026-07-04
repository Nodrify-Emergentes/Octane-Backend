package nodrify.inc.octane.assignments.application.internal.queryservice;

import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.assignments.domain.model.queries.GetMechanicByIdQuery;
import nodrify.inc.octane.assignments.domain.services.MechanicQueryService;
import nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MechanicQueryServiceImpl implements MechanicQueryService {

    private final MechanicRepository mechanicRepository;

    public MechanicQueryServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public Optional<Mechanic> handle(GetMechanicByIdQuery query) {
        return this.mechanicRepository.findById(query.mechanicId());
    }
}
