package nodrify.inc.octane.iam.domain.model.commands;

public record RegisterDeviceCommand(
        String deviceId,
        Long vehicleId
) {
    public RegisterDeviceCommand {
        if (deviceId == null || deviceId.isBlank() ) {
            throw new IllegalArgumentException("Device ID cannot be null or blank.");
        }
        if (vehicleId == null || vehicleId < 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or less than 0.");
        }
    }
}
