package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUserId(Long userId);
}
