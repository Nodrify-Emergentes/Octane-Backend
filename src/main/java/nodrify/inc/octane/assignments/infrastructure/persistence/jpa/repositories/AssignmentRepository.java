package nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.assignments.domain.model.aggregates.Assignment;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentCode;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByOwnerIdAndStatus(Long ownerId, AssignmentStatus status);
    List<Assignment> findByMechanic_Id(Long mechanicId);
    List<Assignment> findByMechanic_IdAndStatusOrderByCreatedAtDesc(Long mechanicId, AssignmentStatus status);
    boolean existsByAssignmentCode(AssignmentCode code);
    boolean existsByOwnerIdAndIdNotAndStatusNot(Long ownerId, Long id, AssignmentStatus status);
    Optional<Assignment> findByAssignmentCode(AssignmentCode code);
}
