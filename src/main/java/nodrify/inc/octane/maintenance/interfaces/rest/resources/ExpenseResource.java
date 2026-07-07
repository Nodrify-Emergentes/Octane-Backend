package nodrify.inc.octane.maintenance.interfaces.rest.resources;


import java.util.List;

public record ExpenseResource(
        Long id,
        String name,
        Double finalPrice,
        String expenseType,
        List<ExpenseItemResource> items
) {
}
