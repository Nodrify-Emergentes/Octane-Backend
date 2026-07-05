package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;

public interface WellnessSummaryCommandService {
    Long handle(GenerateWellnessSummaryCommand generateWellnessSummaryCommand);
}