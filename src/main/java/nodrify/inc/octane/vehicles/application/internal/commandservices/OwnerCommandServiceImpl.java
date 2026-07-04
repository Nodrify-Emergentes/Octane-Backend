package nodrify.inc.octane.vehicles.application.internal.commandservices;

import nodrify.inc.octane.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import nodrify.inc.octane.vehicles.domain.model.aggregates.Owner;
import nodrify.inc.octane.vehicles.domain.model.commands.AddVehicleToOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.CreateOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.DeleteVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.UpdateVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;
import nodrify.inc.octane.vehicles.domain.model.valueobjects.Plate;
import nodrify.inc.octane.vehicles.domain.services.OwnerCommandService;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.ModelRepository;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.VehicleReadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerCommandServiceImpl implements OwnerCommandService {

    private final OwnerRepository ownerRepository;
    private final ProfileRepository profileRepository;
    private final VehicleReadRepository vehicleReadRepository;
    private final ModelRepository modelRepository;

    public OwnerCommandServiceImpl(
            OwnerRepository ownerRepository,
            ProfileRepository profileRepository,
            VehicleReadRepository vehicleReadRepository,
            ModelRepository modelRepository
    ) {
        this.modelRepository = modelRepository;
        this.vehicleReadRepository = vehicleReadRepository;
        this.ownerRepository = ownerRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Owner> handle(CreateOwnerCommand command) {
        var profileOpt = profileRepository.findById(command.profileId());
        if (profileOpt.isEmpty()) {
            return Optional.empty();
        }
        var profile = profileOpt.get();
        var owner = new Owner(profile);
        var savedOwner = ownerRepository.save(owner);
        return Optional.of(savedOwner);
    }

    @Override
    public Optional<Vehicle> handle(AddVehicleToOwnerCommand command) {
        var plateExists = vehicleReadRepository.existsByPlate(new Plate(command.plate()));
        if (plateExists) {
            throw new IllegalArgumentException("Vehicle with given plate already exists.");
        }

        var model = modelRepository.findById(command.modelId());
        if (model.isEmpty()) {
            throw new IllegalArgumentException("Model with given ID does not exist.");
        }

        var owner = ownerRepository.findById(command.ownerId());
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner with given ID does not exist.");
        }

        var ownerEntity = owner.get();
        ownerEntity.AddVehicle(model.get(), command.year(), command.plate());

        try {
            var savedOwner = ownerRepository.saveAndFlush(ownerEntity);
            var savedVehicle = savedOwner.GetVehicles()
                    .stream()
                    .filter(v -> v.getPlate().equals(new Plate(command.plate())))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Vehicle not found after saving"));
            return Optional.of(savedVehicle);
        } catch (Exception e) {
            throw new RuntimeException("Could not save vehicle to owner: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Vehicle> handle(UpdateVehicleFromOwnerCommand command) {
        var ownerOpt = ownerRepository.findById(command.ownerId());
        if (ownerOpt.isEmpty()) {
            throw new IllegalArgumentException("Owner with given ID does not exist.");
        }

        var owner = ownerOpt.get();
        var vehicle = owner.UpdateVehicle(command);

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle with given ID does not exist for this owner.");
        }

        var existingVehicle = vehicleReadRepository.findByPlate(new Plate(command.plate()));
        if (existingVehicle.isPresent() && !existingVehicle.get().getId().equals(vehicle.getId())) {
            throw new IllegalArgumentException("Another vehicle with the given plate already exists.");
        }

        try {
            var savedOwner = ownerRepository.saveAndFlush(owner);
            var savedVehicle = savedOwner.GetVehicles()
                    .stream()
                    .filter(v -> v.getId().equals(vehicle.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Vehicle not found after update."));

            return Optional.of(savedVehicle);

        } catch (Exception e) {
            throw new RuntimeException("Could not update vehicle for owner: " + e.getMessage(), e);
        }
    }


    @Override
    public void handle(DeleteVehicleFromOwnerCommand command) {
        var owner = ownerRepository.findById(command.ownerId());
        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner with given ID does not exist.");
        }

        var ownerEntity = owner.get();
        ownerEntity.DeleteVehicle(command.vehicleId());

        try {
            ownerRepository.save(ownerEntity);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete vehicle from owner: " + e.getMessage(), e);
        }
    }
}
