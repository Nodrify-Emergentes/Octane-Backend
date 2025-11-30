package nrg.inc.bykerz.iam.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.bykerz.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Setter
@Getter
@Entity
public class Device extends AuditableAbstractAggregateRoot<Device> {
    private String deviceId;
    private Long vehicleId;

    public Device() {
        super();
    }

    public Device(String deviceId, Long vehicleId) {
        this.deviceId = deviceId;
        this.vehicleId = vehicleId;
    }
}
