package nodrify.inc.octane.iam.application.internal.queryservices;

import nodrify.inc.octane.iam.domain.model.entities.Role;
import nodrify.inc.octane.iam.domain.model.queries.GetAllRolesQuery;
import nodrify.inc.octane.iam.domain.services.RoleQueryService;
import nodrify.inc.octane.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> handle(GetAllRolesQuery getAllRolesQuery) {
        return roleRepository.findAll();
    }

}
