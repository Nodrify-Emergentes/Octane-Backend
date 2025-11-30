package nrg.inc.bykerz.iam.interfaces.rest.resources;

public record RegisterDeviceResource(
    String deviceId,
    Long vehicleId
) {
}
