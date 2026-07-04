package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.aggregates.Device;
import nodrify.inc.octane.iam.interfaces.rest.resources.DeviceResource;

public class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device device) {
        return new DeviceResource(
                device.getId(),
                device.getDeviceId(),
                device.getVehicleId()
        );
    }
}
