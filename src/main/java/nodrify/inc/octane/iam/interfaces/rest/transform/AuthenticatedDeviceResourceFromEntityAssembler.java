package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.aggregates.Device;
import nodrify.inc.octane.iam.interfaces.rest.resources.AuthenticatedDeviceResource;

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
