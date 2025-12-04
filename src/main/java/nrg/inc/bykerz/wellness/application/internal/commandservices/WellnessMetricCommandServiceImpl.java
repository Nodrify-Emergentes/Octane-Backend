package nrg.inc.bykerz.wellness.application.internal.commandservices;

import nrg.inc.bykerz.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import nrg.inc.bykerz.wellness.domain.model.aggregates.WellnessMetric;
import nrg.inc.bykerz.wellness.domain.model.commands.CreateWellnessMetricCommand;
import nrg.inc.bykerz.wellness.domain.model.commands.DeleteWellnessMetricCommand;
import nrg.inc.bykerz.wellness.domain.model.commands.UpdateWellnessMetricCommand;
import nrg.inc.bykerz.wellness.domain.services.WellnessMetricCommandService;
import nrg.inc.bykerz.wellness.domain.services.WellnessMonitoringService;
import nrg.inc.bykerz.wellness.infrastructure.persistence.jpa.repositories.WellnessMetricRepository;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Optional;

@Service
public class WellnessMetricCommandServiceImpl implements WellnessMetricCommandService {

    private final WellnessMetricRepository wellnessMetricRepository;
    private final WellnessMonitoringService wellnessMonitoringService;
    private final ExternalVehiclesService externalVehicleService;

    public WellnessMetricCommandServiceImpl(WellnessMetricRepository wellnessMetricRepository, WellnessMonitoringService WellnessMonitoringService, ExternalVehiclesService externalVehicleService) {
        this.wellnessMetricRepository = wellnessMetricRepository;
        this.wellnessMonitoringService =  WellnessMonitoringService;
        this.externalVehicleService = externalVehicleService;
    }

    @Override
    public Long handle(CreateWellnessMetricCommand createWellnessMetricCommand) {
        System.out.println("Creating metric...");
        System.out.println("Fetch vehicle by id");
        //Validate the vehicle exists
        var vehicleOpt =externalVehicleService.fetchVehicleById(createWellnessMetricCommand.vehicleId());

        if(vehicleOpt.isEmpty()){
            throw new IllegalArgumentException( "Vehicle with id " + createWellnessMetricCommand.vehicleId() + " does not exist");
        }

        System.out.println("Creating metric...");
        //Create the wellness metric
        var wellnessMetric = new WellnessMetric(createWellnessMetricCommand);

        System.out.println("Saving metric...");
        try{
            var savedMetric = wellnessMetricRepository.save(wellnessMetric);
            // Check for alerts after saving the metric
            wellnessMonitoringService.checkEnvironmentalAlerts(savedMetric);

            return wellnessMetric.getId();
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Optional<WellnessMetric> handle(UpdateWellnessMetricCommand command) {
        var metric = wellnessMetricRepository.findById(command.wellnessMetricId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Wellness Metric with id " + command.wellnessMetricId() + " does not exist"
                ));

        var updatedMetric = metric.updateWellnessMetric(command);

        var savedMetric = wellnessMetricRepository.save(updatedMetric);

        // Check for alerts after updating the metric
        wellnessMonitoringService.checkEnvironmentalAlerts(savedMetric);

        return Optional.of(savedMetric);
    }

    @Override
    public void handle(DeleteWellnessMetricCommand deleteWellnessMetricCommand) {
        var metric = wellnessMetricRepository.findById(deleteWellnessMetricCommand.wellnessMetricId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Wellness Metric with id " + deleteWellnessMetricCommand.wellnessMetricId() + " does not exist"
                ));

        wellnessMetricRepository.delete(metric);
    }
}
