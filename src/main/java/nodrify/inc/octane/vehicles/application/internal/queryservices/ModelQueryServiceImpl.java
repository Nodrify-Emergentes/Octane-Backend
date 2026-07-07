package nodrify.inc.octane.vehicles.application.internal.queryservices;


import nodrify.inc.octane.vehicles.domain.model.aggregates.Model;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllBrandsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetAllModelsQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelByIdQuery;
import nodrify.inc.octane.vehicles.domain.model.queries.GetModelsByBrandQuery;
import nodrify.inc.octane.vehicles.domain.services.ModelQueryService;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelQueryServiceImpl implements ModelQueryService {

    final private ModelRepository modelRepository;

    public ModelQueryServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> handle(GetAllModelsQuery getAllModelsQuery) {
        return modelRepository.findAll();
    }

    @Override
    public Optional<Model> handle(GetModelByIdQuery getModelByIdQuery) {
        return modelRepository.findById(getModelByIdQuery.modelId());
    }

    @Override
    public List<Model> handle(GetModelsByBrandQuery query) {
        return modelRepository.findByBrand(query.brand());
    }

    @Override
    public List<String> handle(GetAllBrandsQuery query) {
        return modelRepository.findAllBrands();
    }
}
