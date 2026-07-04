package nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellnessMetricRepository extends JpaRepository<WellnessMetric, Long> {

    List<WellnessMetric> findByVehicleId(Long vehicleId);



}
