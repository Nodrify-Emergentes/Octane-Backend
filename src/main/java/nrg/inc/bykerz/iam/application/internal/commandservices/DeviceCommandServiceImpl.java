package nrg.inc.bykerz.iam.application.internal.commandservices;

import nrg.inc.bykerz.iam.domain.model.aggregates.Device;
import nrg.inc.bykerz.iam.domain.model.commands.RegisterDeviceCommand;
import nrg.inc.bykerz.iam.domain.model.commands.ValidateDeviceCommand;
import nrg.inc.bykerz.iam.domain.services.DeviceCommandService;
import nrg.inc.bykerz.iam.infrastructure.persistence.jpa.repositories.DeviceRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceRepository deviceRepository;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Optional<Device> handle(RegisterDeviceCommand registerDeviceCommand) {
        return Optional.empty();
    }

    @Override
    public Optional<ImmutablePair<Device, String>> handle(ValidateDeviceCommand validateDeviceCommand) {
        return Optional.empty();
    }
}
