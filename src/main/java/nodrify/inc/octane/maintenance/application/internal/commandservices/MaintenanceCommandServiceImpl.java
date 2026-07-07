package nodrify.inc.octane.maintenance.application.internal.commandservices;

import jakarta.transaction.Transactional;
import nodrify.inc.octane.maintenance.domain.model.agreggates.Maintenance;
import nodrify.inc.octane.maintenance.domain.model.commands.*;
import nodrify.inc.octane.maintenance.domain.model.commands.*;
import nodrify.inc.octane.maintenance.domain.model.entities.MaintenanceState;
import nodrify.inc.octane.maintenance.domain.model.queries.GetExpenseByIdQuery;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.MaintenanceStates;
import nodrify.inc.octane.maintenance.domain.services.ExpenseQueryService;
import nodrify.inc.octane.maintenance.domain.services.MaintenanceCommandService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceRepository;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.VehicleReadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaintenanceCommandServiceImpl implements MaintenanceCommandService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleReadRepository vehicleReadRepository;
    private final ExpenseCommandServiceImpl expenseCommandService;
    private final ExpenseQueryService expenseQueryService;

    public MaintenanceCommandServiceImpl(MaintenanceRepository maintenanceRepository, VehicleReadRepository vehicleReadRepository, ExpenseCommandServiceImpl expenseCommandService, ExpenseQueryService expenseQueryService) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleReadRepository = vehicleReadRepository;
        this.expenseCommandService = expenseCommandService;
        this.expenseQueryService = expenseQueryService;
    }

    @Override
    public Optional<Maintenance> handle(CreateMaintenanceCommand command) {

        var vehicle = vehicleReadRepository.findById(command.vehicleId());

        if (vehicle.isEmpty()) {
            throw new IllegalArgumentException("Vehicle with id " + command.vehicleId() + " not found");
        }

        var maintenance = new Maintenance(
                command.details(),
                command.vehicleId(),
                command.dateOfService(),
                command.location(),
                command.description(),
                new MaintenanceState(MaintenanceStates.PENDING),
                command.mechanicId()
        );


        try {
            maintenanceRepository.save(maintenance);
            return Optional.of(maintenance);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating maintenance: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(DeleteMaintenanceCommand command) {

        var maintenance = maintenanceRepository.findById(command.maintenanceId());

        if (maintenance.isEmpty()) {
            throw new IllegalArgumentException("Maintenance with id " + command.maintenanceId() + " not found");
        }

        try {
            // Delete associated expense if it exists
            if (maintenance.get().getMaintenanceExpense() != null) {
                var expenseId = maintenance.get().getMaintenanceExpense().getId();
                expenseCommandService.handle(new DeleteExpenseCommand(expenseId));
            }
            maintenanceRepository.delete(maintenance.get());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting maintenance: " + e.getMessage());
        }

    }

    @Override
    public Optional<Maintenance> handle(AssignExpenseToMaintenanceCommand command) {

        var expense = expenseQueryService.handle(new GetExpenseByIdQuery(command.expenseId()));
        if (expense.isEmpty()) {
            throw new IllegalArgumentException("Expense with id " + command.expenseId() + " not found");
        }

        var maintenance = maintenanceRepository.findById(command.maintenanceId());
        if (maintenance.isEmpty()) {
            throw new IllegalArgumentException("Maintenance with id " + command.maintenanceId() + " not found");
        }

        maintenance.get().AssignExpenseToMaintenance(expense.get());

        try {
            maintenanceRepository.save(maintenance.get());
            return maintenance;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while assigning expense to maintenance: " + e.getMessage());
        }
    }

    @Override
    public Optional<Maintenance> handle(UpdateStateOfMaintenanceByIdCommand command) {

        var maintenance = maintenanceRepository.findById(command.maintenanceId());

        if (maintenance.isEmpty()) {
            throw new IllegalArgumentException("Maintenance with id " + command.maintenanceId() + " not found");
        }

        maintenance.get().UpdateStateOfMaintenance(new MaintenanceState(MaintenanceStates.valueOf(command.state())));

        try {
            maintenanceRepository.save(maintenance.get());
            return maintenance;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating state of maintenance to " + command.state()+ ":" + e.getMessage());
        }
    }
}
