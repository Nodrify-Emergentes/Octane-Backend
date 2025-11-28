package nrg.inc.bykerz.iam.domain.model.aggregates;

import nrg.inc.bykerz.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

public class Device extends AuditableAbstractAggregateRoot<Device> {
    private String deviceId;
    private String vehicleId;
}
