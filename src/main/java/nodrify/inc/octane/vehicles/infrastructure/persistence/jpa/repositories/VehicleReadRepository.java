package nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.domain.model.valueobjects.Plate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleReadRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlate(Plate plate);
    Optional<Vehicle> findByPlate(Plate plate);

    List<Vehicle> getVehiclesByOwner_Id(Long ownerId);
}