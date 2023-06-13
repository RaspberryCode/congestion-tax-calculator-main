package congestion.calculator.domain.model.calendar;

import congestion.calculator.adapters.holidays.SwedenHolidayService;
import congestion.calculator.domain.ports.HolidayService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static congestion.calculator.domain.model.Util.getDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TollFeeCalendarTest {

    private final HolidayService holidaysService = new SwedenHolidayService();
    private final TollFeeCalendar calculator = new TollFeeCalendar(holidaysService);

    @ParameterizedTest
    @CsvFileSource(resources = "/get-toll-fee.csv", numLinesToSkip = 1)
    void getTollFee(String date, int feeAmount, String dayOfWeek, boolean isAroundHoliday) {
        var tollPassingDateTime = getDate(date).toLocalDate();
        var result = calculator.isDayTollable(tollPassingDateTime);
        assertEquals(!isAroundHoliday, result);
    }
}