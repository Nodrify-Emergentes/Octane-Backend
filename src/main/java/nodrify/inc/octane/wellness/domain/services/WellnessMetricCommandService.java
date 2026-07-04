package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.commands.CreateWellnessMetricCommand;
import nodrify.inc.octane.wellness.domain.model.commands.DeleteWellnessMetricCommand;
import nodrify.inc.octane.wellness.domain.model.commands.UpdateWellnessMetricCommand;

import java.util.Optional;

public interface WellnessMetricCommandService {

    Long handle(CreateWellnessMetricCommand createWellnessMetricCommand);

    Optional<WellnessMetric> handle(UpdateWellnessMetricCommand updateWellnessMetricCommand);

    void handle (DeleteWellnessMetricCommand deleteWellnessMetricCommand);

}
