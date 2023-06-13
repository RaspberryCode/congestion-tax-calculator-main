package congestion.calculator.adapters.schedule;

import congestion.calculator.domain.model.TollFeeScheduleRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreTollFeeScheduleRepository extends JpaRepository<TollFeeScheduleRange, Long> {
}
