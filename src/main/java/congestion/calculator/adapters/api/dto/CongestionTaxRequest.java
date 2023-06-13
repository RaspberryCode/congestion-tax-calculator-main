package congestion.calculator.adapters.api.dto;

import congestion.calculator.domain.model.vehicle.Vehicle;
import congestion.calculator.domain.model.vehicle.VehicleType;

import java.time.LocalDateTime;
import java.util.List;

public record CongestionTaxRequest(VehicleDto vehicle, List<LocalDateTime> dates) {
    public record VehicleDto(VehicleType vehicleType) {
        public Vehicle toDomain() {
            return new Vehicle(this.vehicleType);
        }
    }
}
