package nodrify.inc.octane.reports.domain.services;

import nodrify.inc.octane.reports.interfaces.rest.resources.ReportResource;
import nodrify.inc.octane.reports.domain.model.queries.GetReportByVehicleIdQuery;

import java.util.Optional;

public interface ReportsQueryService {
    Optional<ReportResource> handle(GetReportByVehicleIdQuery query);
}
