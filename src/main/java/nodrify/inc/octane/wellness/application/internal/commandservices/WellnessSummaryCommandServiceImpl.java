package nodrify.inc.octane.wellness.application.internal.commandservices;

import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;
import nodrify.inc.octane.wellness.domain.services.GenerativeAIService;
import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.commands.GenerateWellnessSummaryCommand;
import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessSummary;
import nodrify.inc.octane.wellness.domain.model.queries.GetWellnessMetricsByVehicleIdQuery;
import nodrify.inc.octane.wellness.domain.services.WellnessMetricQueryService;
import nodrify.inc.octane.wellness.domain.services.WellnessSummaryCommandService;
import nodrify.inc.octane.wellness.infrastructure.persistence.jpa.repositories.WellnessSummaryRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class WellnessSummaryCommandServiceImpl implements WellnessSummaryCommandService {

    private final WellnessSummaryRepository wellnessSummaryRepository;
    private final ExternalVehiclesService externalVehiclesService;
    private final WellnessMetricQueryService wellnessMetricQueryService;
    private final GenerativeAIService aiClient;

    public WellnessSummaryCommandServiceImpl(
            WellnessSummaryRepository wellnessSummaryRepository,
            ExternalVehiclesService externalVehiclesService,
            WellnessMetricQueryService wellnessMetricQueryService,
            GenerativeAIService aiClient) {
        this.wellnessSummaryRepository = wellnessSummaryRepository;
        this.externalVehiclesService = externalVehiclesService;
        this.wellnessMetricQueryService = wellnessMetricQueryService;
        this.aiClient = aiClient;
    }

    @Override
    public Optional<WellnessSummary> handle(GenerateWellnessSummaryCommand generateWellnessSummaryCommand) {
        var vehicleId = generateWellnessSummaryCommand.vehicleId();

        var vehicleOpt = externalVehiclesService.fetchVehicleById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            throw new IllegalArgumentException("Vehicle with id " + vehicleId + " does not exist");
        }

        WellnessSummary wellnessSummary;
        var existingSummaryOpt = wellnessSummaryRepository.findByVehicleId(new VehicleId(vehicleId));

        if (existingSummaryOpt.isPresent()) {
            wellnessSummary = existingSummaryOpt.get();
            wellnessSummary.markGenerating();
        } else {
            wellnessSummary = new WellnessSummary(generateWellnessSummaryCommand);
            wellnessSummary.markGenerating();
        }

        var query = new GetWellnessMetricsByVehicleIdQuery(vehicleId);
        List<WellnessMetric> metrics = wellnessMetricQueryService.handle(query);

        var vehicleNaming = vehicleOpt.get().getModel().getBrand() + " " + vehicleOpt.get().getModel().getName() + " " + vehicleOpt.get().getModel().getModelYear();

        var prompt = buildPrompt(vehicleNaming, metrics);
        var aiSummary = aiClient.generateSummary(prompt); //Response

        //Check if its a valid response first
        if (aiSummary == null || aiSummary.trim().isEmpty()) {
            System.out.println("AI generation failed");
            wellnessSummary.markFailed();
        }
        else {
            System.out.println("AI Summary: " + aiSummary);
            wellnessSummary.markFresh(aiSummary);
        }

        var savedSummary = wellnessSummaryRepository.save(wellnessSummary);
        return Optional.of(savedSummary);
    }

    private String buildPrompt(String vehicle, List<WellnessMetric> metrics) {
        var sb = new StringBuilder();
        sb.append("You are analyzing the latest 3 metrics for a customer's motorcycle ").append(vehicle).append(".");
        sb.append("Return a summary between 100 and 200 characters of the vehicle health, mention critical anomalies, and whether it needs inspection.\n");
        sb.append("Use plain language and be actionable, directed at them. If no issues are detected on the most recent metric, the summary must be 'Your vehicle is healthy.'\n\n");
        sb.append("Recent metrics:\n");

        int start = Math.max(0, metrics.size() - 3);
        for (int i = start; i < metrics.size(); i++) {
            var m = metrics.get(i);
            sb.append("Registered at ").append(m.getRegisteredAt().format(DateTimeFormatter.ISO_LOCAL_TIME))
                    .append(", temp=").append(m.getEnvironmentalConditions().temperatureCelsius())
                    .append(", CO2=").append(m.getAirQuality().CO2Ppm())
                    .append(", NH3=").append(m.getAirQuality().NH3Ppm())
                    .append(", Benzene=").append(m.getAirQuality().BenzenePpm())
                    .append(", pressure=").append(m.getAtmosphericPressure().pressureHpa())
                    .append("\n");
        }

        System.out.println(sb);

        return sb.toString();
    }
}
