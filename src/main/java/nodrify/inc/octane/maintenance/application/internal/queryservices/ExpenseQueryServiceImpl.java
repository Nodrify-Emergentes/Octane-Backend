package nodrify.inc.octane.maintenance.application.internal.queryservices;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Expense;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllExpensesByUserIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetExpenseByIdQuery;
import nodrify.inc.octane.maintenance.domain.services.ExpenseQueryService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseQueryServiceImpl implements ExpenseQueryService {

    private final ExpenseRepository expenseRepository;

    public ExpenseQueryServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional(readOnly = true)
    public List<Expense> handle(GetAllExpensesByUserIdQuery query) {
        return expenseRepository.findByUserId(query.userId());
    }

    @Transactional(readOnly = true)
    public Optional<Expense> handle(GetExpenseByIdQuery query) {
        return expenseRepository.findById(query.expenseId());
    }
}
