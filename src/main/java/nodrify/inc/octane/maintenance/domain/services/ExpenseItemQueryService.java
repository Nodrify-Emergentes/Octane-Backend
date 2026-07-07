package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.ExpenseItem;
import nodrify.inc.octane.maintenance.domain.model.queries.GetAllExpenseItemsByExpenseIdQuery;

import java.util.List;

public interface ExpenseItemQueryService {
    List<ExpenseItem> handle(GetAllExpenseItemsByExpenseIdQuery query);
}
