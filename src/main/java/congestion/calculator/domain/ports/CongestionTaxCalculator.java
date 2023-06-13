package congestion.calculator.domain.ports;

import congestion.calculator.domain.model.vehicle.ToableVehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface CongestionTaxCalculator {

    int getTax(ToableVehicle toableVehicle, List<LocalDateTime> dates);
}
