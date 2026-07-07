package nodrify.inc.octane.maintenance.interfaces.rest.resources;

import java.util.List;

public record CreateExpenseResource(
        String name,
        Double finalPrice,
        String expenseType,
        List<CreateExpenseItemResource> items
) {
}
