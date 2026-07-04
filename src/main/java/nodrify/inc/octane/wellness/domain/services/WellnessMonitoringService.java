package nodrify.inc.octane.wellness.domain.services;

import nodrify.inc.octane.wellness.domain.model.aggregates.WellnessMetric;
import nodrify.inc.octane.wellness.domain.model.events.AirQualityAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.AtmosphericPressureAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.EnvironmentalConditionAlertEvent;
import nodrify.inc.octane.wellness.domain.model.events.StatusImpactAlertEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class WellnessMonitoringService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void checkEnvironmentalAlerts(WellnessMetric metric) {
        checkTemperatureAlerts(metric);
        checkAirQualityAlerts(metric);
        checkPressureAlerts(metric);
        checkImpactAlerts(metric);
    }

    private void checkTemperatureAlerts(WellnessMetric metric) {
        Float temperature = metric.getEnvironmentalConditions().temperatureCelsius();

        // Alertas de temperatura
        if (temperature > 40) {
            eventPublisher.publishEvent(new EnvironmentalConditionAlertEvent(
                    this, metric.getVehicleId(), metric.getEnvironmentalConditions(),
                    "HIGH_TEMP", "HIGH"
            ));
        } else if (temperature < 0) {
            eventPublisher.publishEvent(new EnvironmentalConditionAlertEvent(
                    this, metric.getVehicleId(), metric.getEnvironmentalConditions(),
                    "LOW_TEMP", "MEDIUM"
            ));
        }

    }

    private void checkAirQualityAlerts(WellnessMetric metric) {
        Double co2 = metric.getAirQuality().CO2Ppm();
        Double nh3 = metric.getAirQuality().NH3Ppm();
        Double benzene = metric.getAirQuality().BenzenePpm();

        if (co2 > 1000) {
            eventPublisher.publishEvent(new AirQualityAlertEvent(
                    this, metric.getVehicleId(), metric.getAirQuality(),
                    "HIGH_CO2", "HIGH"
            ));
        }

        if (nh3 > 25) {
            eventPublisher.publishEvent(new AirQualityAlertEvent(
                    this, metric.getVehicleId(), metric.getAirQuality(),
                    "HIGH_NH3", "MEDIUM"
            ));
        }

        if (benzene > 5) {
            eventPublisher.publishEvent(new AirQualityAlertEvent(
                    this, metric.getVehicleId(), metric.getAirQuality(),
                    "HIGH_BENZENE", "HIGH"
            ));
        }
    }

    private void checkPressureAlerts(WellnessMetric metric) {
        Float pressure = metric.getAtmosphericPressure().pressureHpa();

        if (pressure < 900) {
            eventPublisher.publishEvent(new AtmosphericPressureAlertEvent(
                    this, metric.getVehicleId(), pressure, "LOW_PRESSURE", "HIGH"
            ));
        } else if (pressure > 1050) {
            eventPublisher.publishEvent(new AtmosphericPressureAlertEvent(
                    this, metric.getVehicleId(), pressure, "HIGH_PRESSURE", "MEDIUM"
            ));
        }
    }

    private void checkImpactAlerts(WellnessMetric metric) {
        Boolean impactDetected = metric.getStatusImpact().impactDetected();

        if (impactDetected) {
            eventPublisher.publishEvent(new StatusImpactAlertEvent(
                    this, metric.getVehicleId(), metric.getStatusImpact().impactDetected(), "IMPACT_DETECTED"
            ));
        }
    }
}
