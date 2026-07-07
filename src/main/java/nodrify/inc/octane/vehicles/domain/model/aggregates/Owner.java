package nodrify.inc.octane.vehicles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import nodrify.inc.octane.profiles.domain.model.aggregates.Profile;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nodrify.inc.octane.vehicles.domain.model.commands.UpdateVehicleFromOwnerCommand;
import nodrify.inc.octane.vehicles.domain.model.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Owner extends AuditableAbstractAggregateRoot<Owner> {

    @OneToOne(optional = false)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    private List<Vehicle> vehicles = new ArrayList<>();

    protected Owner() {
    }

    public Owner(Profile profile) {
        this.profile = profile;
    }

    public Vehicle AddVehicle(Model model, String year, String plate) {
        var vehicle = new Vehicle(model, year, plate);
        vehicle.setOwner(this);
        this.vehicles.add(vehicle);
        return vehicle;
    }

    public List<Vehicle> GetVehicles() { return vehicles; }

    public void DeleteVehicle(Long vehicleId) {
        vehicles.removeIf(v -> v.getId().equals(vehicleId));
    }

    public Vehicle UpdateVehicle(UpdateVehicleFromOwnerCommand command) {
        var opt = this.vehicles.stream()
                .filter(v -> v.getId().equals(command.vehicleId()))
                .findFirst();

        if (opt.isPresent()) {
            var vehicle = opt.get();
            vehicle.UpdateVehicle(command);
            return vehicle;
        }

        return null;
    }
}