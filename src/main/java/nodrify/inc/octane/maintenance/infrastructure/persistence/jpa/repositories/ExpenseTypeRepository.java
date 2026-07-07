package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.maintenance.domain.model.entities.ExpenseType;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.ExpenseTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType,Long> {
    Boolean existsByName(ExpenseTypes name);
}
