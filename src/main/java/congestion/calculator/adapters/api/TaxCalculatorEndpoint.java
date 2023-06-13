package congestion.calculator.adapters.api;

import congestion.calculator.adapters.api.dto.CongestionTaxRequest;
import congestion.calculator.adapters.api.dto.CongestionTaxResponse;
import congestion.calculator.domain.ports.CongestionTaxCalculator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/congestion")
public class TaxCalculatorEndpoint {

    private final CongestionTaxCalculator congestionTaxCalculator;

    public TaxCalculatorEndpoint(CongestionTaxCalculator congestionTaxCalculator) {
        this.congestionTaxCalculator = congestionTaxCalculator;
    }

    @PostMapping("/tax")
    public CongestionTaxResponse calculateFees(@RequestBody CongestionTaxRequest request) {
        var vehicle = request.vehicle()
                             .toDomain();
        var totalFee = congestionTaxCalculator.getTax(vehicle, request.dates());
        return new CongestionTaxResponse(totalFee);
    }
}
