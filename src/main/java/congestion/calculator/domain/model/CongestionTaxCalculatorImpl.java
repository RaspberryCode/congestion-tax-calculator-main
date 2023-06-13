package congestion.calculator.domain.model;

import congestion.calculator.domain.model.calendar.TollFeeCalendar;
import congestion.calculator.domain.model.datesaggregator.DatesIntervalAggregator;
import congestion.calculator.domain.model.feemapper.TimeToFeeMapper;
import congestion.calculator.domain.model.vehicle.ToableVehicle;
import congestion.calculator.domain.ports.CongestionTaxCalculator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class CongestionTaxCalculatorImpl implements CongestionTaxCalculator {
    private final TollFeeCalendar tollFeeCalendar;
    private final DatesIntervalAggregator intervalAggregator;
    private final TimeToFeeMapper timeToFeeMapper;
    private final int maxDailyFee = 60;

    CongestionTaxCalculatorImpl(TollFeeCalendar tollFeeCalendar, DatesIntervalAggregator intervalAggregator, TimeToFeeMapper timeToFeeMapper) {
        this.tollFeeCalendar = tollFeeCalendar;
        this.intervalAggregator = intervalAggregator;
        this.timeToFeeMapper = timeToFeeMapper;
    }

    private Map<LocalDate, List<LocalTime>> groupRecordsByDay(List<LocalDateTime> dates) {
        return dates.stream()
                    .collect(Collectors.groupingBy(LocalDateTime::toLocalDate,
                            Collectors.mapping(LocalDateTime::toLocalTime, Collectors.toList())));
    }

    @Override
    public int getTax(ToableVehicle toableVehicle, List<LocalDateTime> dates) {
        if (dates.isEmpty()) return 0;
        if (toableVehicle.isTollFree()) return 0;

        return groupRecordsByDay(dates).entrySet()
                                       .stream()
                                       .filter(entry -> tollFeeCalendar.isDayTollable(entry.getKey()))
                                       .map(entry -> sumDailyFees(entry.getValue()))
                                       .reduce(Integer::sum)
                                       .orElseThrow(() -> new RuntimeException(
                                               "Cannot calculate congestion fee for specified dates."));
    }

    private Integer sumDailyFees(List<LocalTime> times) {
        return intervalAggregator.aggregateInIntervals(times)
                                 .stream()
                                 .map(this::mapTimesToFees)
                                 .map(this::getBiggestFeeInInterval)
                                 .reduce(Integer::sum)
                                 .map(dailyFee -> Integer.min(dailyFee, maxDailyFee))
                                 .orElseThrow(() -> new RuntimeException("Cannot calculate daily fee"));

    }

    private List<Integer> mapTimesToFees(List<LocalTime> times) {
        return times.stream()
                    .map(timeToFeeMapper::getFee)
                    .toList();
    }

    private Integer getBiggestFeeInInterval(List<Integer> feesInInterval) {
        return feesInInterval.stream()
                             .max(Integer::compareTo)
                             .orElseThrow(() -> new RuntimeException("Cannot extract biggest fee in Interval."));
    }
}
