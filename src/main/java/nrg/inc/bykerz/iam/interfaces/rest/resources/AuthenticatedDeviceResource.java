package nrg.inc.bykerz.iam.interfaces.rest.resources;

public record AuthenticatedDeviceResource(
        Long id,
        String deviceId,
        Long vehicleId,
        String token
) {
}
