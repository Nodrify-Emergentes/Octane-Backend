package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.agreggates.Expense;
import nodrify.inc.octane.maintenance.domain.model.commands.CreateExpenseByOwnerIdCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.CreateExpenseCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.DeleteExpenseCommand;

import java.util.Optional;

public interface ExpenseCommandService {
    Optional<Expense> handle(CreateExpenseCommand command);
    Optional<Expense> handle(CreateExpenseByOwnerIdCommand command);
    void handle(DeleteExpenseCommand command);
}
