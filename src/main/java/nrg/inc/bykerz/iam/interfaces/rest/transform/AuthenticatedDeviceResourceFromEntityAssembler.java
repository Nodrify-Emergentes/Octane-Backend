package nrg.inc.bykerz.iam.interfaces.rest.transform;

import nrg.inc.bykerz.iam.domain.model.aggregates.Device;
import nrg.inc.bykerz.iam.interfaces.rest.resources.AuthenticatedDeviceResource;

public class AuthenticatedDeviceResourceFromEntityAssembler {
    public static AuthenticatedDeviceResource toResourceFromEntity(Device device, String token) {
        return new AuthenticatedDeviceResource(
                device.getId(),
                device.getDeviceId(),
                device.getVehicleId(),
                token
        );
    }
}
