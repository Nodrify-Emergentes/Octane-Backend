package nodrify.inc.octane.vehicles.domain.model.commands;

public record DeleteVehicleFromOwnerCommand(Long ownerId, Long vehicleId) {
}
