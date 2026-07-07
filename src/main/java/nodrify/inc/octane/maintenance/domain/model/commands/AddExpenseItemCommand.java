package nodrify.inc.octane.maintenance.domain.model.commands;

public record AddExpenseItemCommand(
        Long expenseId,
        String name,
        Integer amount,
        Double unitPrice,
        Double totalPrice,
        String itemType
) {
}
