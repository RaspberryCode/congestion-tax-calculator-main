package congestion.calculator.domain.model.datesaggregator;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DatesIntervalAggregator {

    public List<List<LocalTime>> aggregateInIntervals(List<LocalTime> dates) {
        var sortedDates = dates.stream()
                               .sorted()
                               .toList();
        var intervals = new HashMap<TimeRange, List<LocalTime>>();
        for (LocalTime time : sortedDates) {
            intervals.keySet()
                     .stream()
                     .filter(interval -> interval.isInRange(time))
                     .findFirst()
                     .ifPresentOrElse(intervalKey -> addToExistingInterval(intervals, time, intervalKey),
                             () -> initNewInterval(intervals, time));
        }
        return intervals.values()
                        .stream()
                        .toList();
    }

    private void addToExistingInterval(HashMap<TimeRange, List<LocalTime>> intervals, LocalTime date, TimeRange intervalKey) {
        intervals.get(intervalKey)
                 .add(date);
    }

    private void initNewInterval(HashMap<TimeRange, List<LocalTime>> intervals, LocalTime date) {
        var newInterval = new ArrayList<LocalTime>();
        newInterval.add(date);
        intervals.put(new TimeRange(date, date.plusHours(1)), newInterval);
    }
}
