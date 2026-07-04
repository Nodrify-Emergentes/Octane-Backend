package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;


import nodrify.inc.octane.maintenance.domain.model.agreggates.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpenseItemRepository extends JpaRepository<ExpenseItem,Long> {
    @Modifying
    @Transactional
    void deleteExpenseItemsByExpense_Id(Long expenseId);
}

