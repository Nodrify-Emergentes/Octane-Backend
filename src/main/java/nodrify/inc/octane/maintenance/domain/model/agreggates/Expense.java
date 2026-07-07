package nodrify.inc.octane.maintenance.domain.model.agreggates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodrify.inc.octane.maintenance.domain.model.entities.ExpenseType;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Expense extends AuditableAbstractAggregateRoot<Expense> {
    @NotNull
    private String name;

    @NotNull
    private Double finalPrice;

    @NotNull
    private Long userId;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExpenseItem> expenseItems;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    public Expense(String name, Double finalPrice, Long userId, ExpenseType expenseType) {
        this.name = name;
        this.finalPrice = finalPrice;
        this.userId = userId;
        this.expenseType = expenseType;
        this.expenseItems = new ArrayList<>();
    }



}
