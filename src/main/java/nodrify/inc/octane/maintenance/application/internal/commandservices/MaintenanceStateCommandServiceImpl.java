package nodrify.inc.octane.maintenance.application.internal.commandservices;

import nodrify.inc.octane.maintenance.domain.model.commands.SeedMaintenanceStatesCommand;
import nodrify.inc.octane.maintenance.domain.model.entities.MaintenanceState;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.MaintenanceStates;
import nodrify.inc.octane.maintenance.domain.services.MaintenanceStateCommandService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceStateRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MaintenanceStateCommandServiceImpl implements MaintenanceStateCommandService {

    private final MaintenanceStateRepository maintenanceStateRepository;

    public MaintenanceStateCommandServiceImpl(MaintenanceStateRepository maintenanceStateRepository) {
        this.maintenanceStateRepository = maintenanceStateRepository;
    }

    @Override
    public void handle(SeedMaintenanceStatesCommand command) {
        Arrays.stream(MaintenanceStates.values()).forEach(maintenanceState -> {
            if (!maintenanceStateRepository.existsByName(maintenanceState)) {
                maintenanceStateRepository.save(new MaintenanceState(MaintenanceStates.valueOf(maintenanceState.name())));
            }
        });
    }
}
