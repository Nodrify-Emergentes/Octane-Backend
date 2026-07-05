package nodrify.inc.octane.wellness.application.internal.commandservices;

import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.wellness.application.internal.outboundservices.ai.GenAIClient;
import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;
import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricQueryService;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryCommandService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.WellnessSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WellnessSummaryCommandServiceImpl implements WellnessSummaryCommandService {

    private final WellnessSummaryRepository wellnessSummaryRepository;
    private final ExternalVehiclesService externalVehiclesService;
    private final WellnessMetricQueryService wellnessMetricQueryService;
    private final GenAIClient aiClient;

    public WellnessSummaryCommandServiceImpl(
            WellnessSummaryRepository wellnessSummaryRepository,
            ExternalVehiclesService externalVehiclesService,
            WellnessMetricQueryService wellnessMetricQueryService,
            GenAIClient aiClient) {
        this.wellnessSummaryRepository = wellnessSummaryRepository;
        this.externalVehiclesService = externalVehiclesService;
        this.wellnessMetricQueryService = wellnessMetricQueryService;
        this.aiClient = aiClient;
    }

    @Override
    public Long handle(GenerateWellnessSummaryCommand generateWellnessSummaryCommand) {
        var vehicleId = generateWellnessSummaryCommand.vehicleId();

        var vehicleOpt = externalVehiclesService.fetchVehicleById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            throw new IllegalArgumentException("Vehicle with id " + vehicleId + " does not exist");
        }

        var query = new GetWellnessMetricsByVehicleIdQuery(vehicleId);
        List<WellnessMetric> metrics = wellnessMetricQueryService.handle(query);

        var prompt = buildPrompt(vehicleOpt.get().getModel() + " " + vehicleOpt.get().getYear(), metrics);
        var aiSummary = aiClient.generateSummary(prompt);

        var existingSummaryOpt = wellnessSummaryRepository.findByVehicleId(new nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId(vehicleId));

        WellnessSummary summary;
        if (existingSummaryOpt.isPresent()) {
            summary = existingSummaryOpt.get();
            summary.markFresh(aiSummary);
        } else {
            summary = new WellnessSummary();
            summary.markGenerating();
            summary.markFresh(aiSummary);
        }

        var savedSummary = wellnessSummaryRepository.save(summary);
        return savedSummary.getId();
    }

    private String buildPrompt(String vehicle, List<WellnessMetric> metrics) {


        var sb = new StringBuilder();
        sb.append("You are analyzing wellness metrics for vehicle ").append(vehicle).append(".\n");
        sb.append("Return a concise summary between 50 to 100 words of the vehicle health, mention anomalies, and state whether it needs inspection, or needs repair.\n");
        sb.append("Use plain language and be actionable. If no issues are detected on the most recent metric, the summary must be 'Your vehicle is healthy.'\n\n");
        sb.append("Recent metrics:\n");

        int start = Math.max(0, metrics.size() - 10);
        for (int i = start; i < metrics.size(); i++) {
            var m = metrics.get(i);
            sb.append("- metricId=").append(m.getId())
                    .append(", registeredAt=").append(m.getRegisteredAt())
                    .append(", temp=").append(m.getEnvironmentalConditions().temperatureCelsius())
                    .append(", CO2=").append(m.getAirQuality().CO2Ppm())
                    .append(", NH3=").append(m.getAirQuality().NH3Ppm())
                    .append(", Benzene=").append(m.getAirQuality().BenzenePpm())
                    .append(", pressure=").append(m.getAtmosphericPressure().pressureHpa())
                    .append(", impact=").append(m.getStatusImpact().impactDetected())
                    .append("\n");
        }

        return sb.toString();
    }
}
