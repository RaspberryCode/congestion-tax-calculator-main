package congestion.calculator.domain.model.feemapper;

import congestion.calculator.domain.model.TollFeeScheduleRange;
import congestion.calculator.domain.ports.TollFeeScheduleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeToFeeMapper {

    private final TollFeeScheduleRepository tollFeeScheduleRepository;

    public TimeToFeeMapper(TollFeeScheduleRepository tollFeeScheduleRepository) {
        this.tollFeeScheduleRepository = tollFeeScheduleRepository;
    }

    public int getFee(LocalTime time) {
        return tollFeeScheduleRepository.getAll()
                                        .stream()
                                        .filter(timeRange -> timeRange.isInRange(time))
                                        .findFirst()
                                        .map(TollFeeScheduleRange::getFee)
                                        .orElseThrow();
    }
}
