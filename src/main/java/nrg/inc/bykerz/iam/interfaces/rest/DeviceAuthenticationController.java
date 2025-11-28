package nrg.inc.bykerz.iam.interfaces.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicles/devices")
public class DeviceAuthenticationController {
    private final DeviceAuthenticationService deviceAuthenticationService;

    @PostMapping("/authenticate")
}