package nodrify.inc.octane.wellness.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessSummaryByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryCommandService;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryQueryService;
import nodrify.inc.octane.wellness.interfaces.rest.resources.WellnessSummaryResource;
import nodrify.inc.octane.wellness.interfaces.rest.transform.WellnessSummaryResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicle Wellness Summary", description = "Operations related to AI-generated vehicle wellness summaries")
public class WellnessSummaryController {
    private final WellnessSummaryCommandService wellnessSummaryCommandService;
    private final WellnessSummaryQueryService wellnessSummaryQueryService;


    public WellnessSummaryController(
            WellnessSummaryCommandService wellnessSummaryCommandService,
            WellnessSummaryQueryService wellnessSummaryQueryService) {
        this.wellnessSummaryCommandService = wellnessSummaryCommandService;
        this.wellnessSummaryQueryService = wellnessSummaryQueryService;
    }

    @PostMapping("/{vehicleId}/wellness-summary")
    @Operation(summary = "Force generate a wellness summary for a vehicle")
    public ResponseEntity<Void> generateSummaryByVehicleId(@PathVariable Long vehicleId) {
        wellnessSummaryCommandService.handle(new GenerateWellnessSummaryCommand(vehicleId));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{vehicleId}/wellness-summary")
    @Operation(summary = "Get the latest wellness summary for a vehicle")
    public ResponseEntity<WellnessSummaryResource> getSummaryByVehicleId(@PathVariable Long vehicleId) {
        VehicleId vehicle = new VehicleId(vehicleId);
        var getWellnessSummaryByVehicleId = new GetWellnessSummaryByVehicleIdQuery(vehicle);
        var wellnessSummary = wellnessSummaryQueryService.handle(getWellnessSummaryByVehicleId);

        if (wellnessSummary.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var wellnessSummaryEntity = wellnessSummary.get();
        var wellnessSummaryResource = WellnessSummaryResourceFromEntityAssembler.toResourceFromEntity(wellnessSummaryEntity);
        return ResponseEntity.ok(wellnessSummaryResource);
    }
}