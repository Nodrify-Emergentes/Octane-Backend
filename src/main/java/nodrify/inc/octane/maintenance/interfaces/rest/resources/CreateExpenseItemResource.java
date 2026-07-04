package nodrify.inc.octane.maintenance.interfaces.rest.resources;

public record CreateExpenseItemResource(
        String name,
        Integer amount,
        Double unitPrice,
        Double totalPrice,
        String itemType
) {
}
