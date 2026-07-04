package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.maintenance.domain.model.entities.MaintenanceState;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.MaintenanceStates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceStateRepository extends JpaRepository<MaintenanceState,Long> {
    boolean existsByName(MaintenanceStates name);
}
