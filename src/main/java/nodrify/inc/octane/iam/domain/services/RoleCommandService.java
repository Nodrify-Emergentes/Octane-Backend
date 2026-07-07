package nodrify.inc.octane.iam.domain.services;

import nodrify.inc.octane.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {

    void handle(SeedRolesCommand seedRolesCommand);

}
