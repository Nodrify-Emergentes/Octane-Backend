package nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellnessSummaryRepository extends JpaRepository<WellnessSummary, Long> {
    Optional<WellnessSummary> findByVehicleId(VehicleId vehicleId);
}
