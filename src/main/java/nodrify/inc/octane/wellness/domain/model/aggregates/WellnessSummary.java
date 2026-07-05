package nodrify.inc.octane.wellness.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;
import nodrify.inc.octane.wellness.domain.model.valueobjects.SummaryStatus;
import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;

@Getter
@Entity
public class WellnessSummary extends AuditableAbstractAggregateRoot<WellnessSummary> {

    @Embedded
    private VehicleId vehicleId;

    @Enumerated(EnumType.STRING)
    private SummaryStatus status;

    @Column(length = 200)
    private String summary;

    public WellnessSummary() {
        super();
    }

    public WellnessSummary(GenerateWellnessSummaryCommand command) {
        this.vehicleId = new VehicleId(command.vehicleId());
        this.status = SummaryStatus.GENERATING;
    }

    public void markGenerating() {
        this.status = SummaryStatus.GENERATING;
    }

    public void markFresh(String summary) {
        this.status = SummaryStatus.FRESH;
        this.summary = summary;
    }

    public void markFailed(String summary) {
        this.status = SummaryStatus.FAILED;
        this.summary = summary;
    }

    public void markStale(String summary) {
        this.status = SummaryStatus.STALE;
        this.summary = summary;
    }

    public Long getVehicleId() { return vehicleId.vehicleId(); }

    public String getSummaryStatus() { return status.toString(); }
}
