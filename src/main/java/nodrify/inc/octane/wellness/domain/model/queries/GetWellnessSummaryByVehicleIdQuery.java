package nodrify.inc.octane.wellness.domain.model.queries;

import nodrify.inc.octane.wellness.domain.model.valueobjects.VehicleId;

public record GetWellnessSummaryByVehicleIdQuery(VehicleId vehicleId) {
    public GetWellnessSummaryByVehicleIdQuery{
        if(vehicleId.vehicleId()==null || vehicleId.vehicleId() <= 0){
            throw new IllegalArgumentException("Vehicle ID must be a positive non-null value");
        }
    }
}