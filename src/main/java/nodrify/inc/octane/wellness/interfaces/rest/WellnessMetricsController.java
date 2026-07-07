package nodrify.inc.octane.wellness.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodrify.inc.octane.wellness.domain.model.commands.DeleteWellnessMetricCommand;
import nodrify.inc.octane.wellness.domain.model.queries.GetAllWellnessMetricsQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricByIdQuery;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricCommandService;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricQueryService;
import nodrify.inc.octane.wellness.interfaces.rest.resources.CreateWellnessMetricResource;
import nodrify.inc.octane.wellness.interfaces.rest.resources.UpdateWellnessMetricResource;
import nodrify.inc.octane.wellness.interfaces.rest.resources.WellnessMetricResource;
import nodrify.inc.octane.wellness.interfaces.rest.transform.CreateWellnessMetricCommandFromResourceAssembler;
import nodrify.inc.octane.wellness.interfaces.rest.transform.UpdateWellnessMetricCommandFromResourceAssembler;
import nodrify.inc.octane.wellness.interfaces.rest.transform.WellnessMetricResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/metrics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Wellness Metrics", description = "Operations related to wellness metrics")
@RequiredArgsConstructor
public class WellnessMetricsController {

    private final WellnessMetricCommandService wellnessMetricCommandService;
    private final WellnessMetricQueryService wellnessMetricQueryService;
    //

    @PostMapping
    @Operation(summary = "Create a new wellness metric", description = "Creates a new wellness metric for a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wellness metric created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WellnessMetricResource> createWellnessMetric(
            @RequestBody CreateWellnessMetricResource resource){

        //Transform resource to command
        var createWellnessMetricCommand = CreateWellnessMetricCommandFromResourceAssembler.toCommandFromResource(resource);

        //Handle the command
        var wellnessMetricId = wellnessMetricCommandService.handle(createWellnessMetricCommand);

        //Validate the creation
        if (wellnessMetricId == null) {
            return ResponseEntity.badRequest().build();
        }

        //Retrieve the created metric
        var wellnessMetricOpt = wellnessMetricQueryService.handle(new GetWellnessMetricByIdQuery( wellnessMetricId ));

        if (wellnessMetricOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var wellnessMetric = wellnessMetricOpt.get();

        //Transform to resource

        var wellnessMetricResource = WellnessMetricResourceFromEntityAssembler.toResourceFromEntity(wellnessMetric);
        return ResponseEntity.status(201).body(wellnessMetricResource);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a wellness metric", description = "Updates an existing wellness metric")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wellness metric updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Wellness metric not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WellnessMetricResource> updateWellnessMetric(
            @PathVariable Long id,
            @RequestBody UpdateWellnessMetricResource resource) {

        // Transform resource to command
        var updateWellnessMetricCommand = UpdateWellnessMetricCommandFromResourceAssembler.toCommandFromResource(resource, id);

        // Handle the command
        var updatedWellnessMetricOpt = wellnessMetricCommandService.handle(updateWellnessMetricCommand);

        // Validate the update
        if (updatedWellnessMetricOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var wellnessMetric = updatedWellnessMetricOpt.get();

        // Transform to resource
        var wellnessMetricResource = WellnessMetricResourceFromEntityAssembler.toResourceFromEntity(wellnessMetric);
        return ResponseEntity.ok(wellnessMetricResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a wellness metric", description = "Deletes a wellness metric by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Wellness metric deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Wellness metric not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteWellnessMetric(@PathVariable Long id) {
        // Transform resource to command

        var deleteWellnessMetricCommand = new DeleteWellnessMetricCommand(id);

        // Handle the command
        wellnessMetricCommandService.handle(deleteWellnessMetricCommand);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get wellness metric by ID", description = "Retrieves a specific wellness metric by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wellness metric found"),
            @ApiResponse(responseCode = "404", description = "Wellness metric not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WellnessMetricResource> getWellnessMetricById(@PathVariable Long id) {

        // Transform resource to query
        var getWellnessMetricByIdQuery = new GetWellnessMetricByIdQuery(id);

        // Handle the query
        var wellnessMetricOpt = wellnessMetricQueryService.handle(getWellnessMetricByIdQuery);

        // Validate existence
        if (wellnessMetricOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var wellnessMetric = wellnessMetricOpt.get();

        // Transform to resource
        var wellnessMetricResource = WellnessMetricResourceFromEntityAssembler.toResourceFromEntity(wellnessMetric);
        return ResponseEntity.ok(wellnessMetricResource);
    }

    @GetMapping
    @Operation(summary = "Get all wellness metrics", description = "Retrieves all wellness metrics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wellness metrics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WellnessMetricResource>> getAllWellnessMetrics() {

        // Transform resource to query
        var getAllWellnessMetricsQuery = new GetAllWellnessMetricsQuery();

        // Handle the query
        var wellnessMetrics = wellnessMetricQueryService.handle(getAllWellnessMetricsQuery);

        // Transform to resources
        var wellnessMetricResources = wellnessMetrics.stream()
                .map(WellnessMetricResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(wellnessMetricResources);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get wellness metrics by vehicle ID", description = "Retrieves all wellness metrics for a specific vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wellness metrics retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found for vehicle"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WellnessMetricResource>> getWellnessMetricsByVehicleId(@PathVariable Long vehicleId) {
        //Transform resource to query
        var getWellnessMetricsByVehicleIdQuery = new GetWellnessMetricsByVehicleIdQuery(vehicleId);

        // Handle the query
        var wellnessMetrics = wellnessMetricQueryService.handle(getWellnessMetricsByVehicleIdQuery);

        // Validate if metrics is empty for this vehicle
        if (wellnessMetrics.isEmpty()) {
            return ResponseEntity.status(200).body(Collections.emptyList());
        }

        // Transform to resources
        var wellnessMetricResources = wellnessMetrics.stream()
                .map(WellnessMetricResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(wellnessMetricResources);
    }

}
