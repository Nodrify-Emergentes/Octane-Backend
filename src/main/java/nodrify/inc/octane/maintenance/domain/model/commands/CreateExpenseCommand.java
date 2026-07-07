package nodrify.inc.octane.maintenance.domain.model.commands;

public record CreateExpenseCommand(
        String name,
        Double finalPrice,
        Long userId,
        String expenseType
) {
}
