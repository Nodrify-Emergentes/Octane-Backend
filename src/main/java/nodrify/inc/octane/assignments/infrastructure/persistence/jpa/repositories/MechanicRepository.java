package nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    Optional<Mechanic> getMechanicByProfile_Id(Long profileId);
}
