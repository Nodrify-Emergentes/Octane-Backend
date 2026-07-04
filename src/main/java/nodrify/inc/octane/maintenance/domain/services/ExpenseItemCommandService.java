package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.ExpenseItem;
import nodrify.inc.octane.maintenance.domain.model.commands.AddExpenseItemCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.DeleteExpenseItemsByExpenseIdCommand;

import java.util.Optional;

public interface ExpenseItemCommandService {
    Optional<ExpenseItem> handle(AddExpenseItemCommand command);
    void handle(DeleteExpenseItemsByExpenseIdCommand command);
}
