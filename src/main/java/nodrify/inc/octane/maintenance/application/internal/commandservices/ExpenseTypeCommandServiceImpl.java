package nodrify.inc.octane.maintenance.application.internal.commandservices;

import nodrify.inc.octane.maintenance.domain.model.commands.SeedExpenseTypesCommand;
import nodrify.inc.octane.maintenance.domain.model.entities.ExpenseType;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.ExpenseTypes;
import nodrify.inc.octane.maintenance.domain.services.ExpenseTypeCommandService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.ExpenseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExpenseTypeCommandServiceImpl implements ExpenseTypeCommandService {

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeCommandServiceImpl(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }


    @Override
    public void handle(SeedExpenseTypesCommand command) {
        Arrays.stream(ExpenseTypes.values()).forEach(expenseType -> {
            if (!expenseTypeRepository.existsByName(expenseType)) {
                expenseTypeRepository.save(new ExpenseType(ExpenseTypes.valueOf(expenseType.name())));
            }
        });

    }
}
