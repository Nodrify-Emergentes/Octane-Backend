package nodrify.inc.octane.vehicles.domain.model.commands;

public record AddVehicleToOwnerCommand(
        Long ownerId,
        Long modelId,
        String year,
        String plate
) {}
