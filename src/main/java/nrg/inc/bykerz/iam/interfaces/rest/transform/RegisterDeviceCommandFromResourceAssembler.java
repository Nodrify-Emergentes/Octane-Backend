package nrg.inc.bykerz.iam.interfaces.rest.transform;

import nrg.inc.bykerz.iam.domain.model.commands.RegisterDeviceCommand;
import nrg.inc.bykerz.iam.interfaces.rest.resources.RegisterDeviceResource;

public class RegisterDeviceCommandFromResourceAssembler {
    public static RegisterDeviceCommand toCommandFromResource(RegisterDeviceResource registerDeviceResource) {
        return new RegisterDeviceCommand(
                registerDeviceResource.deviceId(),
                registerDeviceResource.vehicleId()
        );
    }
}
