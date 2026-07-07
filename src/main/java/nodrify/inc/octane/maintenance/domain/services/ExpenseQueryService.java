package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Expense;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllExpensesByUserIdQuery;
import nodrify.inc.octane.maintenance.domain.model.queries.GetExpenseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExpenseQueryService {
    List<Expense> handle(GetAllExpensesByUserIdQuery query);
    Optional<Expense> handle(GetExpenseByIdQuery query);
}
