package nodrify.inc.octane.maintenance.domain.services;

import nodrify.inc.octane.maintenance.domain.model.commands.SeedExpenseTypesCommand;

public interface ExpenseTypeCommandService {
    void handle(SeedExpenseTypesCommand command);
}
