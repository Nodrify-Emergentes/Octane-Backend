package nrg.inc.bykerz.iam.domain.services;

import nrg.inc.bykerz.iam.domain.model.aggregates.Device;
import nrg.inc.bykerz.iam.domain.model.commands.RegisterDeviceCommand;
import nrg.inc.bykerz.iam.domain.model.commands.ValidateDeviceCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(RegisterDeviceCommand registerDeviceCommand);

    Optional<ImmutablePair<Device, String>> handle(ValidateDeviceCommand validateDeviceCommand);
}
