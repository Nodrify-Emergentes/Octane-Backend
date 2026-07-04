package nodrify.inc.octane.iam.domain.services;

import nodrify.inc.octane.iam.domain.model.aggregates.Device;
import nodrify.inc.octane.iam.domain.model.commands.RegisterDeviceCommand;
import nodrify.inc.octane.iam.domain.model.commands.ValidateDeviceCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(RegisterDeviceCommand registerDeviceCommand);

    Optional<ImmutablePair<Device, String>> handle(ValidateDeviceCommand validateDeviceCommand);
}
