package nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findOwnerByProfile_Id(Long profileId);
    Optional<Owner> findOwnerByVehicles_Id(Long vehicleId);
}
