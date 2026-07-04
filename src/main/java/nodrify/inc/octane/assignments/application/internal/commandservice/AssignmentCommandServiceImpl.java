package nodrify.inc.octane.assignments.application.internal.commandservice;

import nodrify.inc.octane.assignments.domain.model.aggregates.Assignment;
import nodrify.inc.octane.assignments.domain.model.commands.*;
import nodrify.inc.octane.assignments.domain.model.commands.*;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentCode;
import nodrify.inc.octane.assignments.domain.model.valueobjects.AssignmentStatus;
import nodrify.inc.octane.assignments.domain.services.AssignmentCommandService;
import nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories.AssignmentRepository;
import nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories.MechanicRepository;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalVehiclesService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentCommandServiceImpl implements AssignmentCommandService {

    private final AssignmentRepository assignmentRepository;
    private final MechanicRepository mechanicRepository;
    private final ExternalVehiclesService externalVehiclesService;

    public AssignmentCommandServiceImpl(AssignmentRepository assignmentRepository, MechanicRepository mechanicRepository, ExternalVehiclesService externalVehiclesService) {
        this.assignmentRepository = assignmentRepository;
        this.mechanicRepository = mechanicRepository;
        this.externalVehiclesService = externalVehiclesService;
    }

    @Override
    public Optional<Assignment> handle(CreateAssignmentCommand command) {
        var assigment = new Assignment(command, AssignmentCode.random());
        while (assignmentRepository.existsByAssignmentCode(assigment.getAssignmentCode())){
            assigment.setAssignmentCode(AssignmentCode.random());
        }
        var mechanicOpt = this.mechanicRepository.findById(command.mechanicId());
        if (mechanicOpt.isEmpty()) {
            throw new IllegalArgumentException("Mechanic with id " + command.mechanicId() + " does not exist");
        }
        var mechanic = mechanicOpt.get();
        assigment.setMechanic(mechanicOpt.get());
        mechanic.addAssignment(assigment);
        var createdAssignment = this.assignmentRepository.save(assigment);
        return Optional.of(createdAssignment);
    }

    @Override
    public Optional<Assignment> handle(UpdateAssignmentStatusCommand command) {
        var assignmentOpt = this.assignmentRepository.findById(command.assignmentId());
        if(assignmentOpt.isEmpty()) {
            throw  new IllegalArgumentException("Assignment not found");
        }
        var assignment = assignmentOpt.get();
        assignment.updateStatus(command);
        var updatedAssignment = this.assignmentRepository.save(assignment);
        return Optional.of(updatedAssignment);
    }

    @Override
    public Optional<Assignment> handle(UpdateAssignmentTypeCommand command) {
        var assignmentOpt = this.assignmentRepository.findById(command.assignmentId());
        if (assignmentOpt.isEmpty()) {
            throw  new IllegalArgumentException("Assignment not found");
        }
        var assignment = assignmentOpt.get();
        assignment.updateType(command);
        var updatedAssignment = this.assignmentRepository.save(assignment);
        return Optional.of(updatedAssignment);
    }

    @Override
    public Optional<Assignment> handle(AssignOwnerToAssignmentCommand command) {
        var ownerOpt = externalVehiclesService.getOwnerById(command.ownerId());
        if (ownerOpt.isEmpty()) {
            throw new IllegalArgumentException("Owner with id " + command.ownerId() + " does not exist");
        }
        var code = new AssignmentCode(command.assignmentCode());
        var assignmentOpt = this.assignmentRepository.findByAssignmentCode(code);
        if(assignmentOpt.isEmpty()) {
            throw  new IllegalArgumentException("Assignment not found");
        }
        var assignment = assignmentOpt.get();

        if (assignment.getStatus().equals(AssignmentStatus.ACTIVE)) {
            throw new IllegalArgumentException("Assignment with code " + command.assignmentCode() + " is already active");
        }

        var conflict = this.assignmentRepository
                .existsByOwnerIdAndIdNotAndStatusNot(command.ownerId(), assignment.getId(), AssignmentStatus.CANCELLED);
        if (conflict) {
            throw new IllegalArgumentException("Owner with id " + command.ownerId() + " is already assigned to another non-cancelled assignment");
        }

        assignment.setOwnerId(ownerOpt.get().getId());

        assignment.setStatus(AssignmentStatus.ACTIVE);

        var updatedAssignment = this.assignmentRepository.save(assignment);
        return Optional.of(updatedAssignment);
    }

    @Override
    public void handle(DeleteAssignmentCommand command) {
        var assignmentOpt = this.assignmentRepository.findById(command.assignmentId());
        if(assignmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Assignment not found");
        }
        var assignment = assignmentOpt.get();

        if(!assignment.getStatus().equals(AssignmentStatus.PENDING)) {
            throw  new IllegalArgumentException("Assignment with id " + command.assignmentId() + " is not pending");
        }

        try {
            var mechanic = this.assignmentRepository.findById(command.assignmentId()).get().getMechanic();
            if (mechanic != null) {
                mechanic.removeAssignment(assignment);
                this.mechanicRepository.save(mechanic);
            }
            this.assignmentRepository.delete(assignment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting assignment: " + e.getMessage());
        }
    }
}
