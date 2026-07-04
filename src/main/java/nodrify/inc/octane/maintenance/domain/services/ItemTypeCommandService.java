package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.commands.SeedItemTypesCommand;

public interface ItemTypeCommandService {
    void handle(SeedItemTypesCommand command);
}
