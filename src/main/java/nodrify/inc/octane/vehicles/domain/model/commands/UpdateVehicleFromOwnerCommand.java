package nodrify.inc.octane.vehicles.domain.model.commands;

public record UpdateVehicleFromOwnerCommand(
        Long ownerId,
        Long vehicleId,
        String plate
) {}