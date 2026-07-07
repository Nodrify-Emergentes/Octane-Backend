package nodrify.inc.octane.maintenance.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.MaintenanceStates;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class MaintenanceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private MaintenanceStates name;

    public MaintenanceState(MaintenanceStates name){
        this.name=name;
    }

}
