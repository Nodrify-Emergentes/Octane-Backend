package nodrify.inc.octane.iam.domain.services;

import nodrify.inc.octane.iam.domain.model.entities.Role;
import nodrify.inc.octane.iam.domain.model.queries.GetAllRolesQuery;

import java.util.List;

public interface RoleQueryService {

    List<Role> handle(GetAllRolesQuery getAllRolesQuery);
}
