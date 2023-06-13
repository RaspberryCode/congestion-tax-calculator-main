package congestion.calculator.domain.model.vehicle;

public record Vehicle(VehicleType vehicleType) implements ToableVehicle {
    @Override
    public boolean isTollFree() {
        return vehicleType.isExemptFromFee();
    }
}
