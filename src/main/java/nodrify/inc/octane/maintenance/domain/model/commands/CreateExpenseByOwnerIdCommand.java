package nodrify.inc.octane.maintenance.domain.model.commands;

public record CreateExpenseByOwnerIdCommand(
        String name,
        Double finalPrice,
        Long ownerId,
        String expenseType
) {
}
