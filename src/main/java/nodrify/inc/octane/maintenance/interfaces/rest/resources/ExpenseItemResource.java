package nodrify.inc.octane.maintenance.interfaces.rest.resources;

public record ExpenseItemResource(
        Long id,
        String name,
        Integer amount,
        Double unitPrice,
        Double totalPrice,
        String itemType
) {
}
