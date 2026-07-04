package nodrify.inc.octane.maintenance.domain.model.agreggates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodrify.inc.octane.maintenance.domain.model.entities.MaintenanceState;
import nodrify.inc.octane.shared.domain.model.entity.AuditableModel;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Maintenance extends AuditableModel {

    @NotNull
    private String details;

    @NotNull
    private Long vehicleId;

    @NotNull
    private Date dateOfService;

    @NotNull
    private String location;

    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "maintenance_expense_id", nullable = true)
    private Expense maintenanceExpense;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "maintenance_state_id")
    private MaintenanceState state;

    @NotNull
    private Long mechanicId;

    public Maintenance(
            String details,
            Long vehicleId,
            Date dateOfService,
            String location,
            String description,
            MaintenanceState state,
            Long mechanicId
    ) {
        this.details = details;
        this.vehicleId = vehicleId;
        this.dateOfService = dateOfService;
        this.location = location;
        this.description = description;
        this.state = state;
        this.mechanicId = mechanicId;
    }

    public void AssignExpenseToMaintenance(Expense expense){
        this.maintenanceExpense = expense;
    }

    public void UpdateStateOfMaintenance(MaintenanceState newState){
        this.state = newState;
    }


}
