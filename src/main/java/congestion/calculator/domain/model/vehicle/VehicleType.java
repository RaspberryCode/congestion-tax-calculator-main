package congestion.calculator.domain.model.vehicle;

public enum VehicleType {
    MOTORCYCLE(true),
    CAR(false),
    TRACTOR(false),
    BUS(true),
    EMERGENCY(true),
    DIPLOMAT(true),
    MILITARY(true),
    FOREIGN(true);

    private final boolean isExemptFromFee;

    VehicleType(boolean isExemptFromFee) {
        this.isExemptFromFee = isExemptFromFee;
    }

    public boolean isExemptFromFee() {
        return isExemptFromFee;
    }
}
