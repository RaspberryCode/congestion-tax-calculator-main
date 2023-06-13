package congestion.calculator.adapters.holidays;

import congestion.calculator.domain.ports.HolidayService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class SwedenHolidayService implements HolidayService {
    private final Map<Integer, List<LocalDate>> swedenHolidays = Map.of(2013,
            List.of(LocalDate.of(2013, 1, 1), LocalDate.of(2013, 1, 6), LocalDate.of(2013, 3, 29),
                    LocalDate.of(2013, 3, 31), LocalDate.of(2013, 4, 1), LocalDate.of(2013, 5, 1),
                    LocalDate.of(2013, 5, 9), LocalDate.of(2013, 5, 19), LocalDate.of(2013, 5, 20),
                    LocalDate.of(2013, 6, 6), LocalDate.of(2013, 6, 22), LocalDate.of(2013, 11, 1),
                    LocalDate.of(2013, 12, 25), LocalDate.of(2013, 12, 26), LocalDate.of(2013, 12, 31)));

    @Override
    public boolean isHoliday(LocalDate date) {
        return getHolidaysForYear(date.getYear()).stream()
                                                 .anyMatch(holidayDate -> isHolidayOrDayBefore(date, holidayDate));

    }

    private List<LocalDate> getHolidaysForYear(int year) {
        if (swedenHolidays.containsKey(year)) {
            return swedenHolidays.get(year);
        } else {
            throw new RuntimeException("No data about holidays for specified year.");
        }
    }

    private boolean isHolidayOrDayBefore(LocalDate date, LocalDate holidayDate) {
        return holidayDate.isEqual(date) || holidayDate.minusDays(1)
                                                       .isEqual(date);
    }
}
