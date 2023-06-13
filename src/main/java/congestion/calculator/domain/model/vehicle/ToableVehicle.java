package congestion.calculator.domain.model.vehicle;

public interface ToableVehicle {
    VehicleType vehicleType();

    default boolean isTollFree() {
        return vehicleType().isExemptFromFee();
    }
}
