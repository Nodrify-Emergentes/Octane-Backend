package nodrify.inc.octane.assignments.application.internal.commandservice;

import nodrify.inc.octane.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;
import nodrify.inc.octane.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import nodrify.inc.octane.assignments.domain.model.aggregates.Mechanic;
import nodrify.inc.octane.assignments.domain.model.commands.CreateMechanicCommand;
import nodrify.inc.octane.assignments.domain.services.MechanicCommandService;
import nodrify.inc.octane.assignments.infrastructure.persistence.jpa.repositories.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MechanicCommandServiceImpl implements MechanicCommandService {

    private final MechanicRepository mechanicRepository;
    private final ProfileRepository profileRepository;

    public MechanicCommandServiceImpl(MechanicRepository mechanicRepository, ProfileRepository profileRepository) {
        this.mechanicRepository = mechanicRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Mechanic> handle(CreateMechanicCommand command) {
        var profileOpt = profileRepository.findById(command.profileId());
        if (profileOpt.isEmpty()) {
            return Optional.empty();
        }
        var profile = profileOpt.get();
        var mechanic = new Mechanic(profile);
        var savedMechanic = mechanicRepository.save(mechanic);
        return Optional.of(savedMechanic);
    }

    @Override
    public Optional<Mechanic> handle(UpdateMechanicMembershipTypeCommand updateMechanicMembershipTypeCommand) {
       var mechanicOpt = mechanicRepository.findById(updateMechanicMembershipTypeCommand.mechanicId());

       if (mechanicOpt.isEmpty()) {
           throw new IllegalArgumentException("Invalid mechanic id: " + updateMechanicMembershipTypeCommand.mechanicId());
       } else {
           var mechanic = mechanicOpt.get();
           mechanic.setMembershipType(updateMechanicMembershipTypeCommand);
           return Optional.of(mechanicRepository.save(mechanic));
       }

    }
}
