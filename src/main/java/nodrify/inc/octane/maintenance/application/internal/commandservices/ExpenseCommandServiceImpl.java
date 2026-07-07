package nodrify.inc.octane.maintenance.application.internal.commandservices;

import nodrify.inc.octane.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import nodrify.inc.octane.maintenance.domain.model.agreggates.Expense;
import nodrify.inc.octane.maintenance.domain.model.commands.CreateExpenseByOwnerIdCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.CreateExpenseCommand;
import nodrify.inc.octane.maintenance.domain.model.commands.DeleteExpenseCommand;
import nodrify.inc.octane.maintenance.domain.model.entities.ExpenseType;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.ExpenseTypes;
import nodrify.inc.octane.maintenance.domain.services.ExpenseCommandService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import nodrify.inc.octane.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ExpenseCommandServiceImpl implements ExpenseCommandService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final OwnerRepository ownerRepository;

    public ExpenseCommandServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, ProfileRepository profileRepository, OwnerRepository ownerRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional
    public Optional<Expense> handle(CreateExpenseCommand command) {

        var user = userRepository.findById(command.userId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with id " + command.userId() + " not found");
        }

        var expense = new Expense(
                command.name(),
                command.finalPrice(),
                command.userId(),
                new ExpenseType(ExpenseTypes.valueOf(command.expenseType()))
        );

        try {
            expenseRepository.save(expense);
            return Optional.of(expense);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating expense: " + e.getMessage());
        }
    }

    @Override
    public Optional<Expense> handle(CreateExpenseByOwnerIdCommand command) {

        var owner = ownerRepository.findById(command.ownerId());

        if (owner.isEmpty()) {
            throw new IllegalArgumentException("Owner with id " + command.ownerId() + " not found");
        }

        var profile = profileRepository.findById(owner.get().getProfile().getId());

        if (profile.isEmpty()) {
            throw new IllegalArgumentException("Profile for owner with id " + command.ownerId() + " not found");
        }

        var user = userRepository.findById(profile.get().getUserId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User for profile with id " + profile.get().getId() + " not found");
        }

        var expense = new Expense(
                command.name(),
                command.finalPrice(),
                user.get().getId(),
                new ExpenseType(ExpenseTypes.valueOf(command.expenseType()))
        );

        try {
            expenseRepository.save(expense);
            return Optional.of(expense);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating expense: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(DeleteExpenseCommand command) {
        var expense = expenseRepository.findById(command.expenseId());

        if (expense.isEmpty()) {
            throw new IllegalArgumentException("Expense with id " + command.expenseId() + " not found");
        }

        try {
            expenseRepository.delete(expense.get());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting expense: " + e.getMessage());
        }
    }
}
