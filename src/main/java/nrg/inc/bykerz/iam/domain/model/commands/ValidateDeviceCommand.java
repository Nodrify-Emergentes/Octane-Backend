package nrg.inc.bykerz.iam.domain.model.commands;

public record ValidateDeviceCommand(String deviceId) {
    public ValidateDeviceCommand {
        if (deviceId == null || deviceId.isBlank()) {
            throw new IllegalArgumentException("Device ID cannot be empty");
        }
    }
}
