package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.commands.RegisterDeviceCommand;
import nodrify.inc.octane.iam.interfaces.rest.resources.RegisterDeviceResource;

public class RegisterDeviceCommandFromResourceAssembler {
    public static RegisterDeviceCommand toCommandFromResource(RegisterDeviceResource registerDeviceResource) {
        return new RegisterDeviceCommand(
                registerDeviceResource.deviceId(),
                registerDeviceResource.vehicleId()
        );
    }
}
