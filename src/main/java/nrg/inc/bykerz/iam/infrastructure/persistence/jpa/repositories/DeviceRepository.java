package nrg.inc.bykerz.iam.infrastructure.persistence.jpa.repositories;

import nrg.inc.bykerz.iam.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {
    Optional<Device> findByVehicleId(String vehicleId);
}
