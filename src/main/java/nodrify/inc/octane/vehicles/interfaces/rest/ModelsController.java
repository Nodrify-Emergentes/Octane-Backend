package nodrify.inc.octane.vehicles.interfaces.rest;

import nodrify.inc.octane.vehicles.domain.model.queries.GetAllBrandsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllModelsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelsByBrandQuery;
import nodrify.inc.octane.vehicles.domain.services.ModelQueryService;
import nodrify.inc.octane.vehicles.interfaces.rest.resources.ModelResource;
import nodrify.inc.octane.vehicles.interfaces.rest.transform.ModelResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/models")
@Tag(name = "Models", description = "Operations related to vehicle models")
public class ModelsController {
    private final ModelQueryService modelQueryService;

    public ModelsController(ModelQueryService modelQueryService) {
        this.modelQueryService = modelQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all models")
    public ResponseEntity<List<ModelResource>> getAllModels() {
        var models = modelQueryService.handle(new GetAllModelsQuery());
        var resources = models.stream()
                .map(ModelResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{modelId}")
    @Operation(summary = "Get model by ID")
    public ResponseEntity<ModelResource> getModelById(@PathVariable Long modelId) {
        var model = modelQueryService.handle(new GetModelByIdQuery(modelId));
        return model.map(m -> ResponseEntity.ok(ModelResourceFromEntityAssembler.toResourceFromEntity(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get models by brand")
    public ResponseEntity<List<ModelResource>> getModelsByBrand(@PathVariable String brand) {
        var models = modelQueryService.handle(new GetModelsByBrandQuery(brand));
        var resources = models.stream()
                .map(ModelResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/brands")
    @Operation(summary = "Get all distinct brands")
    public ResponseEntity<List<String>> getAllBrands() {
        var brands = modelQueryService.handle(new GetAllBrandsQuery());
        return ResponseEntity.ok(brands);
    }
}
