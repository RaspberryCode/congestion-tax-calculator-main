package congestion.calculator.domain.model;


import congestion.calculator.domain.model.calendar.TollFeeCalendar;
import congestion.calculator.domain.model.datesaggregator.DatesIntervalAggregator;
import congestion.calculator.domain.model.feemapper.TimeToFeeMapper;
import congestion.calculator.domain.model.vehicle.ToableVehicle;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static congestion.calculator.domain.model.Util.getDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CongestionTaxCalculatorImplTest {
    private static final int SINGLE_TOLL_VALUE = 30;
    private final DatesIntervalAggregator datesIntervalAggregator = new DatesIntervalAggregator();
    private final ToableVehicle toableVehicle = mock(ToableVehicle.class);
    private final TollFeeCalendar tollFeeCalendar = mock(TollFeeCalendar.class);
    private final TimeToFeeMapper timeToFeeMapper = mock(TimeToFeeMapper.class);
    private final CongestionTaxCalculatorImpl calculator = new CongestionTaxCalculatorImpl(tollFeeCalendar,
            datesIntervalAggregator, timeToFeeMapper);

    @BeforeEach
    void beforeAll() {
        when(toableVehicle.isTollFree()).thenReturn(false);
        when(tollFeeCalendar.isDayTollable(any())).thenReturn(true);
        when(timeToFeeMapper.getFee(any())).thenReturn(SINGLE_TOLL_VALUE);
    }

    @Nested
    @Description("The maximum amount per day and toableVehicle is 60 SEK.")
    class dailyFeesCapRule {
        @Test
        void whenInputDatesInOneDaySpan_shouldBeCappedAt60SEK() {
            var dates = List.of(getDate("2013-02-08 06:20:27"),
                    getDate("2013-02-08 06:31:27"), getDate("2013-02-08 07:55:27"), getDate("2013-02-08 12:30:27"),
                    getDate("2013-02-08 15:30:27"), getDate("2013-02-08 17:30:27"));
            int result = calculator.getTax(toableVehicle, dates);
            assertEquals(60, result);
        }

        @Test
        void whenInputDatesInTwoDaySpan_shouldBeCappedAt120SEK() {
            var firstDayMorning = getDate("2013-01-14 08:00:00");
            var secondDayMorning = firstDayMorning.plusDays(1);

            var dates = List.of(firstDayMorning, firstDayMorning.plusHours(2),
                    firstDayMorning.plusHours(4), firstDayMorning.plusHours(6), firstDayMorning.plusHours(8),
                    firstDayMorning.plusHours(10), secondDayMorning, secondDayMorning.plusHours(2),
                    secondDayMorning.plusHours(4), secondDayMorning.plusHours(6), secondDayMorning.plusHours(8),
                    secondDayMorning.plusHours(10));
            int result = calculator.getTax(toableVehicle, dates);
            assertEquals(120, result);
        }
    }

    @Nested
    @Description("""
            A single charge rule applies in Gothenburg. Under this rule, a toableVehicle that passes several tolling
            stations within 60 minutes is only taxed once. The amount that must be paid is the highest one.
            """)
    class singleChargeRule {
        @Test
        void twoTollingStationPassing() {
            var dates = List.of(getDate("2013-02-08 15:15:27"),
                    getDate("2013-02-08 15:35:00"));
            int result = calculator.getTax(toableVehicle, dates);
            assertEquals(SINGLE_TOLL_VALUE, result);
        }

        @Test
        void threeTollingStationPassing() {
            LocalDateTime date = getDate("2013-02-08 14:58:27");
            var dates = List.of(date, date.plusMinutes(20), date.plusMinutes(40));
            int result = calculator.getTax(toableVehicle, dates);
            assertEquals(SINGLE_TOLL_VALUE, result);
        }
    }
}