package nodrify.inc.octane.maintenance.interfaces.rest.transform;

import nodrify.inc.octane.maintenance.domain.model.agreggates.ExpenseItem;
import nodrify.inc.octane.maintenance.interfaces.rest.resources.ExpenseItemResource;

public class ExpenseItemResourceFromEntityAssembler {
    public static ExpenseItemResource toResourceFromEntity(ExpenseItem entity){
        return new ExpenseItemResource(
                entity.getId(),
                entity.getName(),
                entity.getAmount(),
                entity.getUnitPrice(),
                entity.getTotalPrice(),
                entity.getItemType().getName().name()
        );
    }
}
