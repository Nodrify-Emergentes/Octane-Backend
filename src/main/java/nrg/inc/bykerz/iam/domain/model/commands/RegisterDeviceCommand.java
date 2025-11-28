package nrg.inc.bykerz.iam.domain.model.commands;

public record RegisterDeviceCommand(
        String deviceId
) {
    public RegisterDeviceCommand {
        if (deviceId == null || deviceId.isBlank() ) {
            throw new IllegalArgumentException("Device ID cannot be null or blank.");
        }
    }
}
