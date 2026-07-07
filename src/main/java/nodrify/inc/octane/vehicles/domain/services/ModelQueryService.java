package nodrify.inc.octane.vehicles.domain.services;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Model;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllBrandsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllModelsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelsByBrandQuery;

import java.util.List;
import java.util.Optional;

public interface ModelQueryService {

    List<Model> handle(GetAllModelsQuery query);

    Optional<Model> handle(GetModelByIdQuery query);

    List<Model> handle(GetModelsByBrandQuery query);

    List<String> handle(GetAllBrandsQuery query);
}
