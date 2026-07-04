package nodrify.inc.octane.vehicles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nodrify.inc.octane.vehicles.domain.model.commands.CreateModelCommand;

import java.util.Date;

@Getter
@Entity
public class Model extends AuditableAbstractAggregateRoot<Model> {

    private String name;
    private String brand;
    private String modelYear;
    private String originCountry;
    private Date producedAt;
    private String type;
    private String displacement;
    private String potency;
    private String engineType;
    private String engineTorque;
    private String weight;
    private String transmission;
    private String brakes;
    private String tank;
    private String seatHeight;
    private String consumption;
    private Float price;
    private String oilCapacity;
    private String connectivity;
    private String durability;
    private String octane;

    protected Model() {}

    public Model(CreateModelCommand command) {
        this.name = command.name();
        this.brand = command.brand();
        this.modelYear = command.modelYear();
        this.originCountry = command.originCountry();
        this.producedAt = command.producedAt();
        this.type = command.type();
        this.displacement = command.displacement();
        this.potency = command.potency();
        this.engineType = command.engineType();
        this.engineTorque = command.engineTorque();
        this.weight = command.weight();
        this.transmission = command.transmission();
        this.brakes = command.brakes();
        this.tank = command.tank();
        this.seatHeight = command.seatHeight();
        this.consumption = command.consumption();
        this.price = command.price();
        this.oilCapacity = command.oilCapacity();
        this.connectivity = command.connectivity();
        this.durability = command.durability();
        this.octane = command.octane();
    }



}
