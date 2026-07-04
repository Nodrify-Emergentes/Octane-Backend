package nodrify.inc.octane.iam.interfaces.rest.resources;

public record RegisterDeviceResource(
    String deviceId,
    Long vehicleId
) {
}
