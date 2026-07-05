package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;

import java.util.Optional;

public interface WellnessSummaryCommandService {
    Optional<WellnessSummary> handle(GenerateWellnessSummaryCommand generateWellnessSummaryCommand);
}