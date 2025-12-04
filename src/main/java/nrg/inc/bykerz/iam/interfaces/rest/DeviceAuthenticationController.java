package nrg.inc.bykerz.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.bykerz.iam.domain.services.DeviceCommandService;
import nrg.inc.bykerz.iam.interfaces.rest.resources.AuthenticatedDeviceResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.DeviceResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.RegisterDeviceResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.ValidateDeviceResource;
import nrg.inc.bykerz.iam.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import nrg.inc.bykerz.iam.interfaces.rest.transform.RegisterDeviceCommandFromResourceAssembler;
import nrg.inc.bykerz.iam.interfaces.rest.transform.ValidateDeviceCommandFromResourceAssembler;
import nrg.inc.bykerz.iam.interfaces.rest.transform.AuthenticatedDeviceResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/devices/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Device Authentication", description = "Device authentication operations")
public class DeviceAuthenticationController {
    private final DeviceCommandService deviceCommandService;

    public DeviceAuthenticationController(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate device", description = "Validate a registered device with device ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Device validated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Conflict - Device does not exists"),
            }
    )
    public ResponseEntity<AuthenticatedDeviceResource> validateDevice(@RequestBody ValidateDeviceResource validateDeviceResource) {
        var validateDeviceCommand = ValidateDeviceCommandFromResourceAssembler.toCommandFromResource(validateDeviceResource);

        var validatedDevice = deviceCommandService.handle(validateDeviceCommand);

        if (validatedDevice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedDeviceResource = AuthenticatedDeviceResourceFromEntityAssembler.toResourceFromEntity(validatedDevice.get().getLeft(), validatedDevice.get().getRight());
        return ResponseEntity.ok(authenticatedDeviceResource);
    }

    @PostMapping("/register")
    @Operation(summary = "Register device", description = "Register a new device with device ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Device registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<AuthenticatedDeviceResource> registerDevice(@RequestBody RegisterDeviceResource registerDeviceResource) {
        var registerDeviceCommand = RegisterDeviceCommandFromResourceAssembler.toCommandFromResource(registerDeviceResource);

        var device = deviceCommandService.handle(registerDeviceCommand);

        if (device.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var validateDeviceCommand = ValidateDeviceCommandFromResourceAssembler.toCommandFromResource(
                new ValidateDeviceResource(registerDeviceResource.deviceId())
        );

        var validatedDevice = deviceCommandService.handle(validateDeviceCommand);

        if (validatedDevice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedDeviceResource = AuthenticatedDeviceResourceFromEntityAssembler.toResourceFromEntity(validatedDevice.get().getLeft(), validatedDevice.get().getRight());

        return ResponseEntity.ok(authenticatedDeviceResource);
    }
}