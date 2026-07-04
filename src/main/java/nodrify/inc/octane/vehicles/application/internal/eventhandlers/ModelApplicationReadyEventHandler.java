package nodrify.inc.octane.vehicles.application.internal.eventhandlers;

import nodrify.inc.octane.vehicles.domain.model.commands.SeedModelsCommand;
import nodrify.inc.octane.vehicles.domain.services.ModelCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ModelApplicationReadyEventHandler {

    private final ModelCommandService modelCommandService;
    private static final Logger LOGGER= LoggerFactory.getLogger(ModelApplicationReadyEventHandler.class);


    public ModelApplicationReadyEventHandler(ModelCommandService modelCommandService) {
        this.modelCommandService = modelCommandService;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        var applicationName= event.getApplicationContext().getId();
        LOGGER.info("Starting Model Seeding for {} at {}", applicationName,currentTimestamp());
        var seedModelsCommand = new SeedModelsCommand();
        modelCommandService.handle(seedModelsCommand);
        LOGGER.info("Models seeded successfully for application: {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
