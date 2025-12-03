package nrg.inc.bykerz.assignments.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.bykerz.assignments.domain.model.commands.UpdateMechanicMembershipTypeCommand;
import nrg.inc.bykerz.assignments.domain.model.valueobjects.MembershipType;
import nrg.inc.bykerz.profiles.domain.model.aggregates.Profile;
import nrg.inc.bykerz.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Mechanic extends AuditableAbstractAggregateRoot<Mechanic> {

    @OneToOne(optional = false)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;


    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    MembershipType membershipType;

    protected Mechanic() {
    }

    public Mechanic(Profile profile) {
        this.profile = profile;
        membershipType= MembershipType.BRONZE;
    }

    public String getName() {
        return profile.getFirstName() + " " + profile.getLastName();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.setMechanic(this);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        assignment.setMechanic(null);
    }

    public void setMembershipType(UpdateMechanicMembershipTypeCommand updateMechanicMembershipTypeCommand) {
        this.membershipType = updateMechanicMembershipTypeCommand.membershipType();
    }
}
