package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,Long> {
    List<Maintenance> findByVehicleId(Long vehicleId);
    List<Maintenance> findByMechanicId(Long mechanicId);

    List<Maintenance> findByVehicleIdIn(List<Long> vehicleIds);
}
