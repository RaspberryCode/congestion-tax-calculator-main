package congestion.calculator.domain.ports;

import congestion.calculator.domain.model.TollFeeScheduleRange;

import java.util.List;

public interface TollFeeScheduleRepository {
    List<TollFeeScheduleRange> getAll();
}
