package nrg.inc.bykerz.iam.interfaces.rest.transform;

import nrg.inc.bykerz.iam.domain.model.commands.ValidateDeviceCommand;
import nrg.inc.bykerz.iam.interfaces.rest.resources.ValidateDeviceResource;

public class ValidateDeviceCommandFromResourceAssembler {
    public static ValidateDeviceCommand toCommandFromResource(ValidateDeviceResource validateDeviceResource) {
        return new ValidateDeviceCommand(
                validateDeviceResource.deviceId()
        );
    }
}
