package congestion.calculator.adapters.schedule;

import congestion.calculator.domain.model.TollFeeScheduleRange;
import congestion.calculator.domain.ports.TollFeeScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GothenburgTollFeeScheduleRepository implements TollFeeScheduleRepository {
    private final PostgreTollFeeScheduleRepository tollFeeScheduleRepository;

    public GothenburgTollFeeScheduleRepository(PostgreTollFeeScheduleRepository tollFeeScheduleRepository) {
        this.tollFeeScheduleRepository = tollFeeScheduleRepository;
    }

    @Override
    public List<TollFeeScheduleRange> getAll() {
        return tollFeeScheduleRepository.findAll();
    }
}
