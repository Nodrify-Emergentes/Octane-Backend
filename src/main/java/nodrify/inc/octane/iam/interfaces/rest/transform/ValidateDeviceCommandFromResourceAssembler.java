package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.commands.ValidateDeviceCommand;
import nodrify.inc.octane.iam.interfaces.rest.resources.ValidateDeviceResource;

public class ValidateDeviceCommandFromResourceAssembler {
    public static ValidateDeviceCommand toCommandFromResource(ValidateDeviceResource validateDeviceResource) {
        return new ValidateDeviceCommand(
                validateDeviceResource.deviceId()
        );
    }
}
