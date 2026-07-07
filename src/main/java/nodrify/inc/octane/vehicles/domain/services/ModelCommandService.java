package nodrify.inc.octane.vehicles.domain.services;

import nodrify.inc.octane.vehicles.domain.model.commands.CreateModelCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.SeedModelsCommand;
import nodrify.inc.octane.vehicles.domain.model.aggregates.Model;

import java.util.Optional;

public interface ModelCommandService {
    Optional<Model> handle(CreateModelCommand command);

    void handle (SeedModelsCommand command);
}
