package congestion.calculator.domain.ports;

import java.time.LocalDate;

public interface HolidayService {
    boolean isHoliday(LocalDate date);
}
