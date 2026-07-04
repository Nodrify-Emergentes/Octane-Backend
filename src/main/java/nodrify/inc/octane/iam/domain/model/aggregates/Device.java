package nodrify.inc.octane.iam.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

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
