package nodrify.inc.octane.iam.application.internal.commandservices;

import nodrify.inc.octane.iam.application.internal.outboundservices.tokens.TokenService;
import nodrify.inc.octane.iam.domain.model.aggregates.Device;
import nodrify.inc.octane.iam.domain.model.commands.RegisterDeviceCommand;
import nodrify.inc.octane.iam.domain.model.commands.ValidateDeviceCommand;
import nodrify.inc.octane.iam.domain.services.DeviceCommandService;
import nodrify.inc.octane.iam.infrastructure.persistence.jpa.repositories.DeviceRepository;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceRepository deviceRepository;
    private final ExternalVehiclesService externalVehiclesService;
    private final TokenService tokenService;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository, ExternalVehiclesService externalVehiclesService, TokenService tokenService) {
        this.deviceRepository = deviceRepository;
        this.externalVehiclesService = externalVehiclesService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<Device> handle(RegisterDeviceCommand registerDeviceCommand) {
        if (deviceRepository.existsByDeviceId(registerDeviceCommand.deviceId())) {
            var existingDevice = deviceRepository.findByDeviceId(registerDeviceCommand.deviceId());
            existingDevice.ifPresent(deviceRepository::delete);
        }

        var vehicle = externalVehiclesService.fetchVehicleById(registerDeviceCommand.vehicleId());
        if (vehicle.isEmpty()) {
            throw new IllegalArgumentException("Vehicle does not exist");
        }

        var device = new Device(registerDeviceCommand.deviceId(), registerDeviceCommand.vehicleId());
        deviceRepository.save(device);
        return Optional.of(device);
    }

    @Override
    public Optional<ImmutablePair<Device, String>> handle(ValidateDeviceCommand validateDeviceCommand) {
        var device = deviceRepository.findByDeviceId(validateDeviceCommand.deviceId());

        if (device.isEmpty()) {
            throw new IllegalArgumentException("Device does not exist");
        }
        var token = tokenService.generateToken(String.valueOf(device.get().getId()));

        return Optional.of(ImmutablePair.of(device.get(), token));
    }
}
