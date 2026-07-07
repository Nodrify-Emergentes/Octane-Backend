package nodrify.inc.octane.iam.interfaces.rest.resources;

public record AuthenticatedDeviceResource(
        Long id,
        String deviceId,
        Long vehicleId,
        String token
) {
}
