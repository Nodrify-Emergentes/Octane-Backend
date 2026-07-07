package nodrify.inc.octane.wellness.domain.model.aggregates;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nodrify.inc.octane.wellness.domain.model.commands.CreateWellnessMetricCommand;
import nodrify.inc.octane.wellness.domain.model.commands.UpdateWellnessMetricCommand;
import nodrify.inc.octane.wellness.domain.model.valueobjects.*;
import nodrify.inc.octane.wellness.domain.model.valueobjects.*;

import java.time.LocalDateTime;

@Getter
@Entity
public class WellnessMetric extends AuditableAbstractAggregateRoot<WellnessMetric> {

    private Long vehicleId;

    @Embedded
    private Coordinates coordinates;
    @Embedded
    private AirQuality airQuality;
    @Embedded
    private EnvironmentalConditions environmentalConditions;
    @Embedded
    private AtmosphericPressure atmosphericPressure;
    @Embedded
    private StatusImpact statusImpact;

    private LocalDateTime registeredAt;

    protected WellnessMetric() {super();}

    public WellnessMetric(CreateWellnessMetricCommand createWellnessMetricCommand){
        this.vehicleId=createWellnessMetricCommand.vehicleId();
        this.coordinates=new Coordinates(createWellnessMetricCommand.latitude(), createWellnessMetricCommand.longitude());
        this.airQuality=new AirQuality(createWellnessMetricCommand.CO2Ppm(), createWellnessMetricCommand.NH3Ppm(), createWellnessMetricCommand.BenzenePpm());
        this.environmentalConditions=new EnvironmentalConditions(createWellnessMetricCommand.temperatureCelsius());
        this.atmosphericPressure=new AtmosphericPressure(createWellnessMetricCommand.pressureHpa());
        this.statusImpact=new StatusImpact(createWellnessMetricCommand.impactDetected());
        this.registeredAt=LocalDateTime.now();
    }

    public WellnessMetric updateWellnessMetric(UpdateWellnessMetricCommand updateWellnessMetricCommand){

        setCoordinates(updateWellnessMetricCommand.latitude(),updateWellnessMetricCommand.longitude());
        setAirQuality(updateWellnessMetricCommand.CO2Ppm(),updateWellnessMetricCommand.NH3Ppm(),updateWellnessMetricCommand.BenzenePpm());
        setEnvironmentalConditions(updateWellnessMetricCommand.temperatureCelsius());
        setAtmosphericPressure(updateWellnessMetricCommand.pressureHpa());
        setStatusImpact(updateWellnessMetricCommand.impactDetected());

        return this;
    }

    private void setCoordinates(Float latitude, Float longitude){
        this.coordinates=new Coordinates(latitude,longitude);
    }

    private void setAirQuality(Double CO2Ppm, Double NH3Ppm, Double BenzenePpm){
        this.airQuality=new AirQuality(CO2Ppm,NH3Ppm,BenzenePpm);
    }

    private void setEnvironmentalConditions(Float temperatureCelsius){
        this.environmentalConditions=new EnvironmentalConditions(temperatureCelsius);
    }

    private void setAtmosphericPressure(Float pressureHPa) {
        this.atmosphericPressure = new AtmosphericPressure(pressureHPa);
    }

    private void setStatusImpact(Boolean impactDetected) {
        this.statusImpact= new StatusImpact(impactDetected);
    }
}
