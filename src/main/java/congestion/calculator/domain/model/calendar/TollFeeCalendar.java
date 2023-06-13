package congestion.calculator.domain.model.calendar;

import congestion.calculator.domain.ports.HolidayService;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@Component
public class TollFeeCalendar {

    private final HolidayService holidaysService;

    public TollFeeCalendar(HolidayService holidaysService) {
        this.holidaysService = holidaysService;
    }

    public boolean isDayTollable(LocalDate date) {
        if (holidaysService.isHoliday(date)) {
            return false;
        }
        if (isWeekend(date)) {
            return false;
        }
        return !date.getMonth()
                    .equals(Month.JULY);
    }

    private boolean isWeekend(LocalDate date) {
        var dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
