package nodrify.inc.octane.assignments.application.internal.queryservice;

import nodrify.inc.octane.assignments.domain.model.aggregates.Assignment;
import nodrify.inc.octane.assignments.domain.model.queries.*;
import nodrify.inc.octane.assignments.domain.model.queries.*;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentCode;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentStatus;
import nodrify.inc.octane.assignments.domain.services.AssignmentQueryService;
import nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Locale.filter;

@Service
public class AssignmentQueryServiceImpl implements AssignmentQueryService {
    private final AssignmentRepository assignmentRepository;
    public AssignmentQueryServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Optional<Assignment> handle(GetAssignmentByOwnerIdQuery query) {
        return this.assignmentRepository.findByOwnerIdAndStatus(query.ownerId(), AssignmentStatus.ACTIVE);
    }

    @Override
    public List<Assignment> handle(GetAssignmentsByMechanicIdAndStatusQuery query) {
        AssignmentStatus status;
        try {
            status = AssignmentStatus.valueOf(query.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + query.status(), e);
        }

        // Obtener las assignments ya ordenadas por createdAt desc para que las más recientes aparezcan primero
        return this.assignmentRepository.findByMechanic_IdAndStatusOrderByCreatedAtDesc(query.mechanicId(), status);
    }

    @Override
    public Optional<Assignment> handle(GetAssignmentByIdQuery query) {
        return this.assignmentRepository.findById(query.assignmentId());
    }

    @Override
    public Optional<Assignment> handle(GetAssigmentByCodeQuery query) {
        var code = new AssignmentCode(query.assignmentCode());
        return this.assignmentRepository.findByAssignmentCode(code);
    }

    @Override
    public Optional<Assignment> handle(GetAssignmentByVehicleIdQuery getAssignmentByVehicleIdQuery) {
        return Optional.empty();
    }
}
