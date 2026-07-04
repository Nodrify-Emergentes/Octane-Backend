package nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.wellness.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByVehicleId(Long vehicleId);
}
