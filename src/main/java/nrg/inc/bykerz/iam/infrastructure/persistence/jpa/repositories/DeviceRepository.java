package nrg.inc.bykerz.iam.infrastructure.persistence.jpa.repositories;

import nrg.inc.bykerz.iam.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
